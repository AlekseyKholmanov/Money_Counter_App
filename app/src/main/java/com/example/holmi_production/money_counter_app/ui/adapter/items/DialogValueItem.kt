package com.example.holmi_production.money_counter_app.ui.adapter.items

import androidx.annotation.StringRes
import com.example.holmi_production.money_counter_app.model.RecyclerItem

class DialogValueItem(
    val id:Int,
    @StringRes val text: Int,
    val isSelected: Boolean
): RecyclerItem