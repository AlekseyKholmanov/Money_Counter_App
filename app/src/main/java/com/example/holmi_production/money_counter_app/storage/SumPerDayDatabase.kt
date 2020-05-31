package com.example.holmi_production.money_counter_app.storage

import com.example.holmi_production.money_counter_app.model.entity.SumPerDayEntity
import kotlinx.coroutines.flow.Flow

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
interface SumPerDayDatabase {

    suspend fun insertToday(todaySum: Double)

    suspend fun insertAverage(averageSum: Double)

    suspend fun getToday(): SumPerDayEntity

    suspend fun getAverage(): SumPerDayEntity

    fun observeToday(): Flow<SumPerDayEntity>

    fun observeAverage(): Flow<SumPerDayEntity>
}