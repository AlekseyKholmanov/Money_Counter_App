package com.example.holmi_production.money_counter_app.useCases

import com.example.holmi_production.money_counter_app.model.entity.FilterPeriodEntity
import kotlinx.coroutines.flow.Flow

interface GetActivePeriodUseCase {

    suspend fun getLatestPeriod(): FilterPeriodEntity

    fun observeLatestPeriod(): Flow<FilterPeriodEntity>
}