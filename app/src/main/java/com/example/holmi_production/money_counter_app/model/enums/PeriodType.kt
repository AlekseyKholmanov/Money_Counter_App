package com.example.holmi_production.money_counter_app.model.enums

import com.example.holmi_production.money_counter_app.R

enum class PeriodType(val description: Int) {
    DAY(R.string.today),
    WEEK(R.string.this_week),
    MONTH(R.string.this_month),
    YEAR(R.string.this_year),
    CUSTOM(R.string.custom)
}