package com.example.holmi_production.money_counter_app.ui.adapter.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import com.daimajia.swipe.implments.SwipeItemRecyclerMangerImpl
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.Item
import com.example.holmi_production.money_counter_app.ui.adapter.holder.TransactionItemHolder
import com.example.holmi_production.money_counter_app.ui.adapter.items.TransactionItem
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class TransactionItemDelegate(
    private val callback: TransactionItemHolder.Callback,
    private val itemManager: SwipeItemRecyclerMangerImpl
) : AbsListItemAdapterDelegate<TransactionItem, Item, TransactionItemHolder>() {

    companion object {
        const val VIEW_TYPE = R.layout.item_transaction
    }

    override fun onCreateViewHolder(parent: ViewGroup): TransactionItemHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(VIEW_TYPE, parent, false)
        return TransactionItemHolder(callback, itemManager, view)
    }

    override fun isForViewType(item: Item, items: MutableList<Item>, position: Int): Boolean =
        item.viewType == VIEW_TYPE

    override fun onBindViewHolder(
        item: TransactionItem,
        holder: TransactionItemHolder,
        payload: MutableList<Any>
    ) {
        holder.bind(item)
    }

}