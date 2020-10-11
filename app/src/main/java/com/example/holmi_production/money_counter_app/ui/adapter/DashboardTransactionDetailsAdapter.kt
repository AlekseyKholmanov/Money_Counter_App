package com.example.holmi_production.money_counter_app.ui.adapter

import com.example.holmi_production.money_counter_app.model.RecyclerItem
import com.example.holmi_production.money_counter_app.ui.adapter.delegate.dashboardTransactionDetailsDelegate
import com.example.holmi_production.money_counter_app.ui.adapter.delegate.dashboardTransactionDetailsHeaderDelegate
import com.example.holmi_production.money_counter_app.ui.adapter.items.TransactionDashboardHeaderItem
import com.example.holmi_production.money_counter_app.ui.adapter.items.TransactionDashboardItem
import com.hannesdorfmann.adapterdelegates4.AbsDelegationAdapter

class DashboardTransactionDetailsAdapter() : AbsDelegationAdapter<List<RecyclerItem>>() {

    init {
        items = emptyList()
        delegatesManager.apply {

            addDelegate(
                TransactionDashboardItem.VIEW_TYPE,
                dashboardTransactionDetailsDelegate()
            )
            addDelegate(
                TransactionDashboardHeaderItem.VIEW_TYPE,
                dashboardTransactionDetailsHeaderDelegate()
            )
        }
    }

    override fun getItemCount(): Int = items.count()
}