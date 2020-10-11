package com.example.holmi_production.money_counter_app.storage.db

import com.example.holmi_production.money_counter_app.model.entity.CurrenciesEntity
import kotlinx.coroutines.flow.Flow
import org.joda.time.DateTime

interface CurrencyDatabase {

    suspend fun save(entity: CurrenciesEntity)

    suspend fun getCourseByDate(date: DateTime): CurrenciesEntity

    fun getAll(): Flow<List<CurrenciesEntity>?>
}