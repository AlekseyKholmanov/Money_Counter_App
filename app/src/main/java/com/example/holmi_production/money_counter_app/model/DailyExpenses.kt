package com.example.holmi_production.money_counter_app.model

import kotlin.math.abs

class DailyExpenses(val sum: Double, val isPositive: Boolean) {
    companion object {

        fun calculate(list: List<TransactionDetails>): DailyExpenses {
            var sum = list.sumByDouble { it.transaction.sum }
            return (DailyExpenses(abs(sum), sum >= 0.0))
        }
    }
}