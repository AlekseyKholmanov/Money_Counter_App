package com.example.holmi_production.money_counter_app.ui.keyboard_fragment.categoryPickerWithCreate.create_category_dialog

import android.graphics.drawable.ColorDrawable
import com.arellomobile.mvp.InjectViewState
import com.example.holmi_production.money_counter_app.extensions.async
import com.example.holmi_production.money_counter_app.interactor.CategoryInteractor
import com.example.holmi_production.money_counter_app.model.SpDirection
import com.example.holmi_production.money_counter_app.mvp.BasePresenter
import javax.inject.Inject

@InjectViewState
class PresenterCreateCategory @Inject constructor(private val interactor: CategoryInteractor): BasePresenter<ViewCreateCategory>() {



    fun createCategory(categoryName: String, categoryTypes: List<SpDirection>, color:ColorDrawable?) {
        interactor.insert(name = categoryName, types = categoryTypes, color = color)
            .async()
            .doAfterTerminate {
                viewState.dismissDialog()
            }
            .subscribe()
            .keep()
    }

    fun createSubCategory(){
        viewState.dismissDialog()
    }
}