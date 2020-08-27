package com.example.holmi_production.money_counter_app.model.enums

import com.example.holmi_production.money_counter_app.R

enum class PeriodType(val description: Int) {
    TODAY(R.string.today),
    THIS_WEEK(R.string.this_week),
    THIS_MONTH(R.string.this_month),
    THIS_YEAR(R.string.this_year),
    CUSTOM(R.string.custom)
}