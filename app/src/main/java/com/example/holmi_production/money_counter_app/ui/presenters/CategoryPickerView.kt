package com.example.holmi_production.money_counter_app.ui.presenters

import com.arellomobile.mvp.MvpView
import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity
import com.example.holmi_production.money_counter_app.model.entity.SubCategoryEntity

interface CategoryPickerView : MvpView {
    fun showCategories(categories: ArrayList<Pair<CategoryEntity, List<SubCategoryEntity>>>)
    fun showMessage(show: Boolean, messageResId: Int? = null)
    fun showCreateDialog(it: Array<CategoryEntity>)
    fun showToast(text: String)
    fun updateSubcategories(subcategories: List<SubCategoryEntity>)
}