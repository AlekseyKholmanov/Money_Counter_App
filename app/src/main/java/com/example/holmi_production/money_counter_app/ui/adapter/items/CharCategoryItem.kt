package com.example.holmi_production.money_counter_app.ui.adapter.items

import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.RecyclerItem
import com.example.holmi_production.money_counter_app.ui.adapter.decorators.LeftPaddedMarker

class CharCategoryItem(
    val categoryName: String,
    val sum: Double,
    val percentage: Double,
    val color: Int?,
    val categoryImage: Int?
) : RecyclerItem, LeftPaddedMarker {
    companion object {
        const val shortType: Int = R.layout.item_percentage_categories
        const val detailsType:Int = R.layout.item_categories_details
    }

    override val padding: Int
        get() = 50

}