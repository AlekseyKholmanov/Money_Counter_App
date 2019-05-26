package com.example.holmi_production.money_counter_app.costs.adapter

import com.example.holmi_production.money_counter_app.costs.ListItem
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter

class CostsAdapter:ListDelegationAdapter<List<ListItem>>(){
    init {
        delegatesManager.addDelegate(CostsItemDelegate())
        delegatesManager.addDelegate(CostTimeDividerDelegate())
    }
}