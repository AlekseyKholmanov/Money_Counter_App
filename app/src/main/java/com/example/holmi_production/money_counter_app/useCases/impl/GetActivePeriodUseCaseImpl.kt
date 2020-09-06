package com.example.holmi_production.money_counter_app.useCases.impl

import com.example.holmi_production.money_counter_app.model.entity.FilterPeriodEntity
import com.example.holmi_production.money_counter_app.storage.db.PeriodsDatabase
import com.example.holmi_production.money_counter_app.useCases.GetActivePeriodUseCase
import kotlinx.coroutines.flow.Flow

class GetActivePeriodUseCaseImpl(
    private val periodsDatabase: PeriodsDatabase
): GetActivePeriodUseCase {

    override suspend fun getLatestPeriod(): FilterPeriodEntity {
        return periodsDatabase.getPeriod()
    }

    override fun observeLatestPeriod(): Flow<FilterPeriodEntity> {
        return periodsDatabase.observePeriod()
    }
}