package com.example.holmi_production.money_counter_app.ui.adapter.items

import androidx.annotation.StringRes
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.RecyclerItem
import com.example.holmi_production.money_counter_app.ui.adapter.decorators.FullLineMarker
import com.example.holmi_production.money_counter_app.ui.adapter.decorators.LeftPaddedMarker

class DialogValueItem(
    val id: Int,
    @StringRes val text: Int,
    val isSelected: Boolean
) : RecyclerItem {
    companion object {
        val VIEW_TYPE = R.layout.item_bottom_dialog
    }
}

class DialogValueTextItem(
    val id: Int,
    val text: String,
    val isSelected: Boolean
) : RecyclerItem {
    companion object {
        val VIEW_TYPE = R.layout.item_bottom_dialog
    }
}

class DialogValueGuidItem(
    val id: String,
    val text: String,
    val isSelected: Boolean
) : RecyclerItem {
    companion object {
        val VIEW_TYPE = R.layout.item_bottom_dialog
    }
}

class DialogFirstItem(
    val text: String
) : RecyclerItem, FullLineMarker {
    companion object {
        val VIEW_TYPE = R.layout.item_bottom_dialog_first
    }
}