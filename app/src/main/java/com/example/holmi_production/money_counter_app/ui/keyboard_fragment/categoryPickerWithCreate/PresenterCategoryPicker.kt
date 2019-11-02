package com.example.holmi_production.money_counter_app.ui.keyboard_fragment.categoryPickerWithCreate

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.extensions.async
import com.example.holmi_production.money_counter_app.interactor.CategoryInteractor
import com.example.holmi_production.money_counter_app.mvp.BasePresenter
import javax.inject.Inject

@InjectViewState
class PresenterCategoryPicker @Inject constructor(private val interactor: CategoryInteractor) :
    BasePresenter<ViewCategoryPicker>() {
    fun observeCategories() {
        interactor.observeCategoriesAndSubCategories()
            .async()
            .subscribe ({
                if(it.size==0)
                    viewState.showMessage(true, R.string.information_empty_list)
                else{
                    viewState.showMessage(show = false)
                    viewState.showCategories(it)
                }
            },{
                Log.d("M_PresenterCategPicker","${it.message}")
                viewState.showMessage(true, R.string.error_list)
            }).keep()
    }

    fun getDialogData(){
        interactor.getCategories()
            .async()
            .subscribe ({
                viewState.showCreateDialog(it!!)
            },{
                Log.d("M_PresenterCatPick",it.message)
            })
            .keep()

    }
}