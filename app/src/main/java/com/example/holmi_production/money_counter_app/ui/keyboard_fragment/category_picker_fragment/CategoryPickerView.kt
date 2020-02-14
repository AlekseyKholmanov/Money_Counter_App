package com.example.holmi_production.money_counter_app.ui.keyboard_fragment.category_picker_fragment

import com.arellomobile.mvp.MvpView
import com.example.holmi_production.money_counter_app.model.entity.Category
import com.example.holmi_production.money_counter_app.model.entity.SubCategory

interface CategoryPickerView : MvpView {
    fun showCategories(categories: ArrayList<Pair<Category, List<SubCategory>>>)
    fun showMessage(show: Boolean, messageResId: Int? = null)
    fun showCreateDialog(it: Array<Category>)
    fun showToast(text: String)
    fun updateSubcategories(subcategories: List<SubCategory>)
}