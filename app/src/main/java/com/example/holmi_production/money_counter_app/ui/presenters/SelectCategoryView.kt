package com.example.holmi_production.money_counter_app.ui.presenters

import moxy.MvpView
import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity
import com.example.holmi_production.money_counter_app.model.entity.SubCategoryEntity

interface SelectCategoryView : MvpView {

    fun showCategories(categories: ArrayList<Pair<CategoryEntity, List<SubCategoryEntity>>>)

    fun showMessage(show: Boolean, messageResId: Int? = null)

    fun showToast(text: String)
    fun popUp()

}