package com.example.holmi_production.money_counter_app.ui.adapter.items

import com.example.holmi_production.money_counter_app.model.Item
import com.example.holmi_production.money_counter_app.model.entity.SubCategoryEntity
import com.example.holmi_production.money_counter_app.ui.adapter.delegates.SelectCategoryDelegate

class CategoryItem(
    val index: Int,
    val categoryId: String?,
    val description: String,
    val color: Int,
    val imageResId: Int,
    val subcategories: List<SubCategoryEntity>
) : Item {
    override val viewType: Int
        get() = SelectCategoryDelegate.VIEW_TYPE
}