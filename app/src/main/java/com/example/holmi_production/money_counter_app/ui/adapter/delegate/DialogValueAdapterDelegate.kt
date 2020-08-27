package com.example.holmi_production.money_counter_app.ui.adapter.delegate

import androidx.core.content.ContextCompat
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.RecyclerItem
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegate
import kotlinx.android.synthetic.main.item_dialog_value.view.*
import com.example.holmi_production.money_counter_app.ui.adapter.items.DialogValueItem

fun dialogValueItemAdapterDelegate(itemClickedListener: (Int) -> Unit) =
    adapterDelegate<DialogValueItem, RecyclerItem>(R.layout.item_dialog_value) {
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