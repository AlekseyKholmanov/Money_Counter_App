package com.example.holmi_production.money_counter_app.storage.impl

import com.example.holmi_production.money_counter_app.model.entity.SpendingDetails
import com.example.holmi_production.money_counter_app.model.entity.SpendingEntity
import com.example.holmi_production.money_counter_app.orm.ExpenseDatabase
import com.example.holmi_production.money_counter_app.storage.SpendingDatabase
import io.reactivex.Single
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext


class SpendingDatabaseImpl(
    database: ExpenseDatabase
) : SpendingDatabase {
    private val dao = database.spendingDao()

    override suspend fun insert(spending: SpendingEntity) {
        withContext(Dispatchers.IO) {
            dao.forceInsert(spending)
        }
    }

    override fun observeSpendings(): Flow<List<SpendingEntity>> {
        return dao.observeSpending()
    }

    override suspend fun getSpendings(): List<SpendingEntity> {
        return withContext(Dispatchers.IO){
            dao.getSpendings()
        }
    }

    override suspend fun delete(spending: SpendingEntity) {
        withContext(Dispatchers.IO) {
            dao.delete(spending)
        }
    }

    override suspend fun deleteAll() {
        withContext(Dispatchers.IO) {
            dao.deleteAll()
        }
    }

    override fun observeSpendingsDetails(): Flow<List<SpendingDetails>> {
        return dao.observeSpendingDetails()
    }
}