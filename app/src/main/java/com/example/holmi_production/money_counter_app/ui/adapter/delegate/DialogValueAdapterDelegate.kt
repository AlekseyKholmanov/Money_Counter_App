package com.example.holmi_production.money_counter_app.ui.adapter.delegate

import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.RecyclerItem
import com.example.holmi_production.money_counter_app.ui.adapter.items.DialogValueGuidItem
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegate
import com.example.holmi_production.money_counter_app.ui.adapter.items.DialogValueItem
import com.example.holmi_production.money_counter_app.ui.adapter.items.DialogValueTextItem
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

fun dialogValueTextItemAdapterDelegate(itemClickedListener: (Int) -> Unit) =
    adapterDelegate<DialogValueTextItem, RecyclerItem>(DialogValueItem.VIEW_TYPE) {
        bind {
            with(itemView) {
                dialogItem.text = item.text
                dialogItem.isChecked = item.isSelected
            }
            itemView.setOnClickListener {
                itemClickedListener(item.id)
            }
        }
    }

fun dialogValueGuidItemAdapterDelegate(itemClickedListener: (String) -> Unit) =
    adapterDelegate<DialogValueGuidItem, RecyclerItem>(DialogValueItem.VIEW_TYPE) {
        bind {
            with(itemView) {
                dialogItem.text = item.text
                dialogItem.isChecked = item.isSelected
            }
            itemView.setOnClickListener {
                itemClickedListener(item.id)
            }
        }
    }