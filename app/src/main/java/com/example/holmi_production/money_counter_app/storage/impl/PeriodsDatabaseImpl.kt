package com.example.holmi_production.money_counter_app.storage.impl

import com.example.holmi_production.money_counter_app.model.entity.FilterPeriodEntity
import com.example.holmi_production.money_counter_app.orm.ExpenseDatabase
import com.example.holmi_production.money_counter_app.storage.PeriodsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext


class PeriodsDatabaseImpl(
    database: ExpenseDatabase
) : PeriodsDatabase {
    companion object {
        const val key = "DATE"
    }

    private val dao = database.periodsDao()

    suspend fun insert(period: FilterPeriodEntity) {
        withContext(Dispatchers.IO) { dao.insert(period.copy(id = key)) }
    }

    suspend fun getPeriod(): FilterPeriodEntity {
        return withContext(Dispatchers.IO) { dao.getPeriod(key) }
    }

    fun observePeriod(): Flow<FilterPeriodEntity> {
        return dao.observePeriod(key)
    }
}