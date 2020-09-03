package com.example.holmi_production.money_counter_app.ui._old_presenters

import com.example.holmi_production.money_counter_app.interactor.CategoryInteractor
class CreateCategoryPresenter (private val interactor: CategoryInteractor)  {

//
//    fun createCategory(category: CategoryEntity) {
//        interactor.insert(category)
//            .async()
//            .doAfterTerminate {
//                viewState.popUp()
//            }
//            .subscribe()
//            .keep()
//    }
//
//    fun createSubCategory(name: String, parentId: Int, color: Int) {
//        val subCategory = SubCategoryEntity(categoryId = parentId, description = name, color = color)
//        interactor.insert(subCategory)
//            .async()
//            .subscribe {
//                viewState.popUp()
//            }
//            .keep()
//    }
}