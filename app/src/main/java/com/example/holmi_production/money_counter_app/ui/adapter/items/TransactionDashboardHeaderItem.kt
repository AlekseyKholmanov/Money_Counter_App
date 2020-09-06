package com.example.holmi_production.money_counter_app.ui.adapter.items

import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.Item
import com.example.holmi_production.money_counter_app.model.RecyclerItem
import com.example.holmi_production.money_counter_app.ui.adapter.decorators.FullLineMarker
import com.example.holmi_production.money_counter_app.ui.adapter.delegate.TransactionDayHeaderDelegate
import org.joda.time.DateTime

class TransactionDashboardHeaderItem(
    val date:DateTime,
    val total: Double
) : RecyclerItem, FullLineMarker {

    companion object{
        val VIEW_TYPE = R.layout.item_dashboard_transaction_header
    }

}