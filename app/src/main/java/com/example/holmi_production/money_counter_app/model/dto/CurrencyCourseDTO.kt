package com.example.holmi_production.money_counter_app.model.dto

import com.example.holmi_production.money_counter_app.model.enums.CurrencyType
import org.joda.time.DateTime

class CurrencyCourseDTO (
    val date: DateTime,
    val courses: Map<CurrencyType, Float>
)