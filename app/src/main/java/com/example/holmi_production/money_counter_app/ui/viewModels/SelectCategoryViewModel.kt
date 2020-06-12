package com.example.holmi_production.money_counter_app.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.holmi_production.money_counter_app.interactor.CategoryInteractor
import com.example.holmi_production.money_counter_app.model.Item
import com.example.holmi_production.money_counter_app.model.toItem
import com.example.holmi_production.money_counter_app.storage.SettingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class SelectCategoryViewModel(
    private val interactor: CategoryInteractor,
    private val settingRepository: SettingRepository
) : ViewModel() {

    private val _categories: MutableLiveData<List<Item>> = MutableLiveData()
    val categories: LiveData<List<Item>> = _categories

    fun observeCategories() {
        viewModelScope.launch {
            interactor.observeCategoriesDetails()
                .map { it.map { it.toItem() } }
                .flowOn(Dispatchers.IO)
                .collect {
                    _categories.value = it
                }
        }
    }

    fun categorySelected(categoryId: Int) {
        settingRepository.setCategoryId(categoryId)
    }
}