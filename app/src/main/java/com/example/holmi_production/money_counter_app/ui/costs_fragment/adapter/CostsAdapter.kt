package com.example.holmi_production.money_counter_app.ui.costs_fragment.adapter

import com.example.holmi_production.money_counter_app.model.ListItem
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter

class CostsAdapter(
) : ListDelegationAdapter<List<ListItem>>() {
    init {
        delegatesManager.addDelegate(CostsItemDelegate())
        delegatesManager.addDelegate(CostTimeDividerDelegate())
    }

}