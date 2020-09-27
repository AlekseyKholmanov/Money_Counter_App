package com.example.holmi_production.money_counter_app.ui.adapter.items

import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.RecyclerItem
import com.example.holmi_production.money_counter_app.ui.adapter.decorators.FullLineMarker

class ChartCategoryHeaderItem(
    val spendingType: String,
    val sum: Double,
) : RecyclerItem, FullLineMarker {

    companion object {
        const val VIEW_TYPE: Int = R.layout.item_categories_header
    }

}