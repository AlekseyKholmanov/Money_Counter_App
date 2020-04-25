package com.example.holmi_production.money_counter_app.ui.adapter

import com.example.holmi_production.money_counter_app.model.ListItem
import com.example.holmi_production.money_counter_app.ui.adapter.delegates.CostsItemDelegate
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter

class PieAdapter(
) : ListDelegationAdapter<List<ListItem>>() {
    init {
        delegatesManager.addDelegate(CostsItemDelegate())
    }
}