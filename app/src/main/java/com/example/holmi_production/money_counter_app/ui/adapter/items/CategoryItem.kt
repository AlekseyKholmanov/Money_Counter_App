package com.example.holmi_production.money_counter_app.ui.adapter.items

import com.example.holmi_production.money_counter_app.model.Item
import com.example.holmi_production.money_counter_app.storage.SubCategoryDatabase
import com.example.holmi_production.money_counter_app.ui.adapter.delegates.SelectCategoryDelegate

class CategoryItem(
    val categoryId: Int,
    val description: String,
    val color: Int,
    val imageId: Int?,
    val withSubcategory: Boolean
) : Item {
    override val viewType: Int
        get() = SelectCategoryDelegate.VIEW_TYPE
}