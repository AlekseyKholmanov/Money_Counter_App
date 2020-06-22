package com.example.holmi_production.money_counter_app.ui.adapter

import com.example.holmi_production.money_counter_app.model.Item
import com.example.holmi_production.money_counter_app.ui.adapter.delegates.TransactionItemDelegate
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class PieAdapter(
) : ListDelegationAdapter<List<Item>>() {
    init {
//        delegatesManager.addDelegate(TransactionItemDelegate())
    }
}