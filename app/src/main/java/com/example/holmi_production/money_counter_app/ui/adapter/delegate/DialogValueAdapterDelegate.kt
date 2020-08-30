package com.example.holmi_production.money_counter_app.ui.adapter.delegate

import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.RecyclerItem
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegate
import com.example.holmi_production.money_counter_app.ui.adapter.items.DialogValueItem
import kotlinx.android.synthetic.main.item_bottom_dialog_value.view.*

fun dialogValueItemAdapterDelegate(itemClickedListener: (Int) -> Unit) =
    adapterDelegate<DialogValueItem, RecyclerItem>(DialogValueItem.VIEW_TYPE) {
        bind {
            with(itemView) {
                dialogItem.text = context.getText(item.text)
                dialogItem.isChecked = item.isSelected
            }
            itemView.setOnClickListener {
                itemClickedListener(item.id)
            }
        }
    }