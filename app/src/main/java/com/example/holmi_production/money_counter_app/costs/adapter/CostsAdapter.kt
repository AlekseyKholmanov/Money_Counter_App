package com.example.holmi_production.money_counter_app.costs.adapter

import com.example.holmi_production.money_counter_app.costs.ListItem
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter
import java.text.FieldPosition

class CostsAdapter:ListDelegationAdapter<MutableList<ListItem>>(){
    init {
        delegatesManager.addDelegate(CostsItemDelegate())
        delegatesManager.addDelegate(CostTimeDividerDelegate())
    }

    fun removeItem(position: Int){
        this.items.removeAt(position)
    }

}