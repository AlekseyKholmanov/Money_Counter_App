package com.example.holmi_production.money_counter_app.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.holmi_production.money_counter_app.interactor.CategoryInteractor
import com.example.holmi_production.money_counter_app.model.CategoryDetails
import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity
import com.example.holmi_production.money_counter_app.useCases.GetCategoryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class CategoryDetailsViewModel(
    private val categoryInteractor: CategoryInteractor,
    private val getCategoryUseCase: GetCategoryUseCase
) : ViewModel() {

    private val _categories: MutableLiveData<CategoryDetails?> = MutableLiveData()
    val categories: LiveData<CategoryDetails?> = _categories

    fun createCategory(categoryEntity: CategoryEntity) {
        viewModelScope.launch {
            categoryInteractor.insert(categoryEntity)
        }
    }

    fun observeCategoryById(categoryId: String?) {
        viewModelScope.launch {
            getCategoryUseCase.observeCategoryById(categoryId)
                .flowOn(Dispatchers.IO)
                .collect {
                    _categories.value = it
                }
        }
    }

}