package com.example.holmi_production.money_counter_app.model

import com.example.holmi_production.money_counter_app.model.entity.SpendingDetails
import kotlin.math.abs

class DailyExpenses(val sum: Double, val isPositive: Boolean) {
    companion object {

        fun calculate(list: List<SpendingDetails>): DailyExpenses {
            var sum = 0.0
            for (spending in list)
                if (spending.spending.isSpending == SpDirection.SPENDING) sum -= spending.spending.sum else sum += spending.spending.sum
            return(DailyExpenses(abs(sum),sum>=0.0))
        }
    }
}