package com.example.holmi_production.money_counter_app.ui.adapter.items

import com.example.holmi_production.money_counter_app.model.DailyExpenses
import com.example.holmi_production.money_counter_app.model.Item
import com.example.holmi_production.money_counter_app.model.TransactionDetails
import com.example.holmi_production.money_counter_app.ui.adapter.delegates.TransactionDayHeaderDelegate
import org.joda.time.DateTime
import kotlin.math.abs

class TransactionDayHeaderItem(
    val date:DateTime,
    val sum: Double
) : Item {

    override val viewType: Int
        get() = TransactionDayHeaderDelegate.VIEW_TYPE

}