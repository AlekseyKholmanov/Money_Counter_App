package com.example.holmi_production.money_counter_app.ui.presenters

import androidx.lifecycle.ViewModel
import com.example.holmi_production.money_counter_app.interactor.CategoryInteractor
import com.example.holmi_production.money_counter_app.storage.SettingRepository


class SelectCategoryViewModel(
    private val interactor: CategoryInteractor,
    private val settingRepository: SettingRepository
) : ViewModel() {
    fun observeCategories() {
        interactor.observeCategoriesAndSubCategories()
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