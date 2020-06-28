package com.example.holmi_production.money_counter_app.useCases.impl

import com.example.holmi_production.money_counter_app.model.entity.FilterPeriodEntity
import com.example.holmi_production.money_counter_app.storage.PeriodsDatabase
import com.example.holmi_production.money_counter_app.useCases.GetLatestActivePeriodUseCase
import kotlinx.coroutines.flow.Flow

class GetLatestActivePeriodUseCaseImpl(
    private val periodsDatabase: PeriodsDatabase
): GetLatestActivePeriodUseCase {

    override suspend fun getLatestPeriod(): FilterPeriodEntity {
        return periodsDatabase.getPeriod()
    }

    override fun observeLatestPeriod(): Flow<FilterPeriodEntity> {
        return periodsDatabase.observePeriod()
    }
}