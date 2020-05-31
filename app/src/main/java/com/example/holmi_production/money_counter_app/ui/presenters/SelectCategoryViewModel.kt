package com.example.holmi_production.money_counter_app.ui.presenters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.holmi_production.money_counter_app.interactor.CategoryInteractor
import com.example.holmi_production.money_counter_app.model.CategoryDetails
import com.example.holmi_production.money_counter_app.storage.SettingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch


class SelectCategoryViewModel(
    private val interactor: CategoryInteractor,
    private val settingRepository: SettingRepository
) : ViewModel() {

    private val _categories: MutableLiveData<List<CategoryDetails>> = MutableLiveData()
    val categories: LiveData<List<CategoryDetails>> = _categories

    fun observeCategories() {
        viewModelScope.launch {
            interactor.observeCategoriesDetails()
                .flowOn(Dispatchers.IO)
                .collect {
                    _categories.value = it
                }
        }
//            .async()
//            .subscribe({
//                if (it.size == 0)
//                    viewState.showMessage(true, R.string.information_empty_list)
//                else {
//                    viewState.showMessage(show = false)
//                    viewState.showCategories(it)
//                }
//            }, {
//                Log.d("M_PresenterCategPicker", "${it.message}")
//                viewState.showMessage(true, R.string.error_list)
//            }).keep()
    }

    fun categorySelected(categoryId: Int) {
        settingRepository.setCategoryId(categoryId)
//        viewState.popUp()
    }
}