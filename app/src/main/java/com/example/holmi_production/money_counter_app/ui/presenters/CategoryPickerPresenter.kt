package com.example.holmi_production.money_counter_app.ui.presenters

import android.util.Log
import moxy.InjectViewState
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.extensions.async
import com.example.holmi_production.money_counter_app.interactor.CategoryInteractor
import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity
import com.example.holmi_production.money_counter_app.model.entity.SubCategoryEntity
import com.example.holmi_production.money_counter_app.mvp.BasePresenter
import javax.inject.Inject

@InjectViewState
class CategoryPickerPresenter(private val interactor: CategoryInteractor) :
    BasePresenter<CategoryPickerView>() {
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

    fun insertCategory(category: CategoryEntity) {
        interactor.insert(category)
            .async()
            .subscribe {
                viewState.showToast("категория обновлена")
            }
            .keep()
    }

    fun createSubCategory(subCategory: SubCategoryEntity) {
        interactor.insert(subCategory)
            .async()
            .doAfterTerminate {
                interactor.getCategoryWithSub(subCategory.categoryId)
                    .async()
                    .subscribe({ (_, subcategoryList) ->
                        val nonDeletedSubCategories = subcategoryList.filter { !it.isDeleted }
                        viewState.updateSubcategories(nonDeletedSubCategories)
                    }, {})
            }
            .subscribe {
                viewState.showToast("подкатегория добавлена")
            }
            .keep()
    }

    fun deleteSubcategory(subCategory: SubCategoryEntity) {
        interactor.insert(subCategory.copy(isDeleted = true))
            .async()
            .subscribe()
            .keep()

    }
}