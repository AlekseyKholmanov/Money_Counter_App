package com.example.holmi_production.money_counter_app.ui.adapter

import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.Item
import com.example.holmi_production.money_counter_app.ui.adapter.delegates.TransactionDayHeaderDelegate
import com.example.holmi_production.money_counter_app.ui.adapter.delegates.TransactionItemDelegate
import com.example.holmi_production.money_counter_app.ui.adapter.delegates.ZeroItemAdapterDelegate
import com.example.holmi_production.money_counter_app.ui.adapter.diffUtil.TransactionDiffutil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class TransactionAdapter(
) : AsyncListDifferDelegationAdapter<Item>(TransactionDiffutil()) {
    init {
        delegatesManager.addDelegate(TransactionItemDelegate())
        delegatesManager.addDelegate(TransactionDayHeaderDelegate())
        delegatesManager.addDelegate(ZeroItemAdapterDelegate(R.layout.item_transaction_0data))
    }

}