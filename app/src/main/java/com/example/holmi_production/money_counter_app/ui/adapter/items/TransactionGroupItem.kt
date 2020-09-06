package com.example.holmi_production.money_counter_app.ui.adapter.items

import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.RecyclerItem
import com.example.holmi_production.money_counter_app.model.TransactionDetails
import com.example.holmi_production.money_counter_app.model.entity.TransactionEntity
import com.example.holmi_production.money_counter_app.model.uiModels.DashboardTransactionDetails
import org.joda.time.DateTime

class TransactionGroupItem(
    val date: DateTime,
    val items: List<DashboardTransactionDetails>
) : RecyclerItem {
    companion object {
        val VIEW_TYPE = R.layout.item_transaction_group
    }
}