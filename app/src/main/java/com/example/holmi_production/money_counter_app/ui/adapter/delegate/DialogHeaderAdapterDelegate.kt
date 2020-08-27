package com.example.holmi_production.money_counter_app.ui.adapter.delegate

import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.RecyclerItem
import com.example.holmi_production.money_counter_app.ui.adapter.items.DialogHeaderItem
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegate
import kotlinx.android.synthetic.main.item_dialog_header.view.*

fun dialogHeaderItemDelegate() =
    adapterDelegate<DialogHeaderItem, RecyclerItem>(R.layout.item_dialog_header) {
        bind {
            with(itemView) {
                header.text = context.getText(item.description)
            }
        }
    }