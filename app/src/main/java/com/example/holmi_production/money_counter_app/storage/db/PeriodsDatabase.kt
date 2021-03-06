package com.example.holmi_production.money_counter_app.storage.db

import com.example.holmi_production.money_counter_app.model.entity.FilterPeriodEntity
import com.example.holmi_production.money_counter_app.model.enums.PeriodType
import kotlinx.coroutines.flow.Flow
import org.joda.time.DateTime

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
interface PeriodsDatabase {

    suspend fun addPeriod(left: DateTime, right: DateTime, type: PeriodType)

    suspend fun getPeriod(): FilterPeriodEntity

    fun observePeriod(): Flow<FilterPeriodEntity>
}