package com.example.holmi_production.money_counter_app.ui.keyboard_fragment.categoryPickerWithCreate

import com.arellomobile.mvp.InjectViewState
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.interactor.CategoryInteractor
import com.example.holmi_production.money_counter_app.model.SpDirection
import com.example.holmi_production.money_counter_app.mvp.BasePresenter
import javax.inject.Inject

@InjectViewState
class PresenterCategoryPicker @Inject constructor(private val interactor: CategoryInteractor) :
    BasePresenter<ViewCategoryPicker>() {
    fun observeCategories() {
        interactor.observeCategories()
            .subscribe ({
                if(it.size==0)
                    viewState.showMessage(true, R.string.information_empty_list)
                else{
                    viewState.showMessage(show = false)
                    viewState.showCategories(it)
                }
            },{
                viewState.showMessage(true, R.string.error_list)
            }).keep()
    }
}