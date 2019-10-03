package com.example.holmi_production.money_counter_app.model

import com.example.holmi_production.money_counter_app.model.entity.Spending
import kotlin.math.abs

class DailyExpenses(val sum: Double, val isPositive: Boolean) {
    companion object {
        fun calculate(list: List<Spending>): DailyExpenses {
            var sum = 0.0
            for (spending in list)
                if (spending.isSpending) sum -= spending.sum else sum += spending.sum
            return(DailyExpenses(abs(sum),sum>=0.0))
        }
    }
}