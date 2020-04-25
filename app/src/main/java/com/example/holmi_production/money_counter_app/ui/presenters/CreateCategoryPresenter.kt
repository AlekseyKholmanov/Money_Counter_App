package com.example.holmi_production.money_counter_app.ui.presenters

import com.arellomobile.mvp.InjectViewState
import com.example.holmi_production.money_counter_app.extensions.async
import com.example.holmi_production.money_counter_app.interactor.CategoryInteractor
import com.example.holmi_production.money_counter_app.model.entity.Category
import com.example.holmi_production.money_counter_app.model.entity.SubCategory
import com.example.holmi_production.money_counter_app.mvp.BasePresenter
import javax.inject.Inject

@InjectViewState
class CreateCategoryPresenter @Inject constructor(private val interactor: CategoryInteractor) :
    BasePresenter<CreateCategoryView>() {


    fun createCategory(category: Category) {
        interactor.insert(category)
            .async()
            .doAfterTerminate {
                viewState.dismissDialog()
            }
            .subscribe()
            .keep()
    }

    fun createSubCategory(name: String, parentId: Int, color: Int) {
        val subCategory = SubCategory(parentId = parentId, description = name, color = color)
        interactor.insert(subCategory)
            .async()
            .subscribe {
                viewState.dismissDialog()
            }
            .keep()
    }
}