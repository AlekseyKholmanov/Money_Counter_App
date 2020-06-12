package com.example.holmi_production.money_counter_app.ui.adapter.delegates

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.holmi_production.money_counter_app.model.Item
import com.example.holmi_production.money_counter_app.ui.adapter.holder.ZeroItemHolder
import com.example.holmi_production.money_counter_app.ui.adapter.items.ZeroItem
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class ZeroItemAdapterDelegates(val resId: Int) :
    AbsListItemAdapterDelegate<ZeroItem, Item, ZeroItemHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup): ZeroItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(resId, parent, false)
        return ZeroItemHolder(view)
    }

    override fun isForViewType(item: Item, items: MutableList<Item>, position: Int): Boolean =
        item.viewType == resId

    override fun onBindViewHolder(p0: ZeroItem, p1: ZeroItemHolder, p2: MutableList<Any>) = Unit
}