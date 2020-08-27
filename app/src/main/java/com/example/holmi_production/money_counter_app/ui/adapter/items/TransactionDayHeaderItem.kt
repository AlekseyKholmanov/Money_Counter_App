package com.example.holmi_production.money_counter_app.ui.adapter.items

import com.example.holmi_production.money_counter_app.model.Item
import com.example.holmi_production.money_counter_app.ui.adapter.delegate.TransactionDayHeaderDelegate
import org.joda.time.DateTime

class TransactionDayHeaderItem(
    val date:DateTime,
    val sum: Double
) : Item {

    override val viewType: Int
        get() = TransactionDayHeaderDelegate.VIEW_TYPE

}