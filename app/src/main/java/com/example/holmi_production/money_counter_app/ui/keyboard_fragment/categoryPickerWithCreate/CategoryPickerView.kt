package com.example.holmi_production.money_counter_app.ui.keyboard_fragment.categoryPickerWithCreate

import com.arellomobile.mvp.MvpView
import com.example.holmi_production.money_counter_app.model.entity.Category

interface CategoryPickerView:MvpView {
    fun showCategories(categories: MutableList<Category>)
    fun showMessage(show:Boolean, messageResId:Int? = null)
}