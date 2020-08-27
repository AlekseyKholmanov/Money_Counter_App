package com.example.holmi_production.money_counter_app.ui.adapter

import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.Item
import com.example.holmi_production.money_counter_app.ui.adapter.delegate.SelectCategoryDelegate
import com.example.holmi_production.money_counter_app.ui.adapter.delegate.ZeroItemAdapterDelegate
import com.example.holmi_production.money_counter_app.ui.adapter.diffUtil.CategoryDiffutill
import com.example.holmi_production.money_counter_app.ui.adapter.holder.SelectCategoryHolder
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class SelectCategoryAdapter(
    callback: SelectCategoryHolder.Callback
) : AsyncListDifferDelegationAdapter<Item>(CategoryDiffutill()) {

    init {
        delegatesManager.addDelegate(SelectCategoryDelegate(callback))
        delegatesManager.addDelegate(ZeroItemAdapterDelegate(R.layout.item_category_0data))
    }

}