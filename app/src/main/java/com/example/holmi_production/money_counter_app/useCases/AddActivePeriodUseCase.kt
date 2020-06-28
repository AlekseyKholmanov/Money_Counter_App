package com.example.holmi_production.money_counter_app.useCases

import org.joda.time.DateTime

interface AddActivePeriodUseCase {

    suspend fun addActivePeriod(leftBorder: DateTime, rightBorder: DateTime)

}