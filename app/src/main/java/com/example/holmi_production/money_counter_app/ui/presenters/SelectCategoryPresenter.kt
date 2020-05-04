package com.example.holmi_production.money_counter_app.ui.presenters

import android.util.Log
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.extensions.async
import com.example.holmi_production.money_counter_app.interactor.CategoryInteractor
import com.example.holmi_production.money_counter_app.mvp.BasePresenter
import com.example.holmi_production.money_counter_app.storage.SettingRepository
import moxy.InjectViewState

@InjectViewState
class SelectCategoryPresenter(
    private val interactor: CategoryInteractor,
    private val settingRepository: SettingRepository
) : BasePresenter<SelectCategoryView>() {
    fun observeCategories() {
        interactor.observeCategoriesAndSubCategories()
            .async()
            .subscribe({
                if (it.size == 0)
                    viewState.showMessage(true, R.string.information_empty_list)
                else {
                    viewState.showMessage(show = false)
                    viewState.showCategories(it)
                }
            }, {
                Log.d("M_PresenterCategPicker", "${it.message}")
                viewState.showMessage(true, R.string.error_list)
            }).keep()
    }

    fun categorySelected(categoryId: Int) {
        settingRepository.setCategoryId(categoryId)
        viewState.popUp()
    }
}