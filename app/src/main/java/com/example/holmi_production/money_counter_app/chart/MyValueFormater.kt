package com.example.holmi_production.money_counter_app.chart

import com.example.holmi_production.money_counter_app.extensions.toDateFormat
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.formatter.ValueFormatter

class MyValueFormater : ValueFormatter() {
    override fun getFormattedValue(value: Float, axis: AxisBase?): String {
        return if (axis is XAxis) {
            value.toDateFormat()
        } else {
            value.toString()
        }
    }
}