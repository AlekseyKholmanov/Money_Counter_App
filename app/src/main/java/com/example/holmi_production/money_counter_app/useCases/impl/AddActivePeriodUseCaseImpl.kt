package com.example.holmi_production.money_counter_app.useCases.impl

import com.example.holmi_production.money_counter_app.extensions.withTimeAtEndOfDay
import com.example.holmi_production.money_counter_app.storage.PeriodsDatabase
import com.example.holmi_production.money_counter_app.useCases.AddActivePeriodUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.joda.time.DateTime

class AddActivePeriodUseCaseImpl(
    private val periodsDatabase: PeriodsDatabase
) : AddActivePeriodUseCase {

    override suspend fun addActivePeriod(leftBorder: DateTime, rightBorder: DateTime) {
        withContext(Dispatchers.IO) {
            periodsDatabase.addPeriod(
                left = leftBorder.withTimeAtStartOfDay(),
                right = rightBorder.withTimeAtEndOfDay()
            )
        }
    }
}