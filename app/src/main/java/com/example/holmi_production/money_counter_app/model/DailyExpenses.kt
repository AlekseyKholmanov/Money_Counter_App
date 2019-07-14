package com.example.holmi_production.money_counter_app.model

import kotlin.math.abs

class DailyExpenses(val sum: Double, val isPositive: Boolean) {
    companion object {
        fun calculate(list: List<Spending>): DailyExpenses {
            var sum = 0.0
            for (expense in list)
                if (expense.categoryTypes.isSpending) sum -= expense.sum else sum += expense.sum
            return(DailyExpenses(abs(sum),sum>=0.0))
        }
    }
}