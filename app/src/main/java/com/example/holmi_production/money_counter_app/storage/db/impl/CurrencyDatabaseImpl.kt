package com.example.holmi_production.money_counter_app.storage.db.impl

import com.example.holmi_production.money_counter_app.model.entity.CurrenciesEntity
import com.example.holmi_production.money_counter_app.orm.CurrencyDao
import com.example.holmi_production.money_counter_app.storage.db.CurrencyDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.joda.time.DateTime

class CurrencyDatabaseImpl(
    val dao: CurrencyDao
) : CurrencyDatabase {
    override suspend fun save(entity: CurrenciesEntity) = withContext(Dispatchers.IO) {
        dao.insert(entity)
        Unit
    }

    override suspend fun getCourseByDate(date: DateTime): CurrenciesEntity =
        withContext(Dispatchers.IO) {
            dao.getByDate(date)
        }

    override  fun getAll(): Flow<List<CurrenciesEntity>> {
        return dao.getAll()
    }
}