package com.example.holmi_production.money_counter_app.ui.charts_fragments.pie

import com.example.holmi_production.money_counter_app.model.ListItem
import com.example.holmi_production.money_counter_app.ui.costs_fragment.adapter.CostsItemDelegate
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter

class PieAdapter(
) : ListDelegationAdapter<List<ListItem>>() {
    init {
        delegatesManager.addDelegate(CostsItemDelegate())
    }
}