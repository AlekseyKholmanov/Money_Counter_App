package com.example.holmi_production.money_counter_app.storage.db.impl

import com.example.holmi_production.money_counter_app.extensions.withTimeAtEndOfDay
import com.example.holmi_production.money_counter_app.model.entity.FilterPeriodEntity
import com.example.holmi_production.money_counter_app.model.enums.PeriodType
import com.example.holmi_production.money_counter_app.orm.PeriodsDao
import com.example.holmi_production.money_counter_app.storage.db.PeriodsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.joda.time.DateTime


class PeriodsDatabaseImpl(
    private val dao: PeriodsDao
) : PeriodsDatabase {
    companion object {
        const val key = "DATE"
    }

    override suspend fun addPeriod(left: DateTime, right: DateTime, type: PeriodType) {
        val entity = FilterPeriodEntity(
            id = key,
            type = type,
            from = left,
            to = right
        )
        withContext(Dispatchers.IO) { dao.insert(entity) }
    }

    override suspend fun getPeriod(): FilterPeriodEntity {
        return withContext(Dispatchers.IO) { dao.getPeriod(key) }
    }

    override fun observePeriod(): Flow<FilterPeriodEntity> {
        return dao.observePeriod(key)
            .map {
                it ?: FilterPeriodEntity(
                        key,
                        from = DateTime().withTimeAtStartOfDay(),
                        to = DateTime().withTimeAtEndOfDay()
                    )
            }
    }
}