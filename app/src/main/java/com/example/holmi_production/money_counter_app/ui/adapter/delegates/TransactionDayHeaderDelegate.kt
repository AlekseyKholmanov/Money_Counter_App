package com.example.holmi_production.money_counter_app.ui.adapter.delegates

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.Item
import com.example.holmi_production.money_counter_app.ui.adapter.holder.TransactionDayHeaderHolder
import com.example.holmi_production.money_counter_app.ui.adapter.items.TransactionDayHeaderItem
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class TransactionDayHeaderDelegate : AbsListItemAdapterDelegate
<TransactionDayHeaderItem, Item, TransactionDayHeaderHolder>() {

    companion object {
        const val VIEW_TYPE = R.layout.item_transaction_day_header
    }

    override fun onCreateViewHolder(parent: ViewGroup): TransactionDayHeaderHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(VIEW_TYPE, parent, false)
        return TransactionDayHeaderHolder(view)
    }

    override fun isForViewType(item: Item, items: MutableList<Item>, position: Int): Boolean =
        item.viewType == VIEW_TYPE

    override fun onBindViewHolder(
        item: TransactionDayHeaderItem,
        holder: TransactionDayHeaderHolder,
        payload: MutableList<Any>
    ) {
        holder.bind(item)
    }


}