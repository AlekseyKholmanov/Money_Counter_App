package com.example.holmi_production.money_counter_app.storage.impl

import com.example.holmi_production.money_counter_app.model.TransactionDetails
import com.example.holmi_production.money_counter_app.model.entity.TransactionEntity
import com.example.holmi_production.money_counter_app.orm.ExpenseDatabase
import com.example.holmi_production.money_counter_app.storage.TransactionDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext


class TransactionDatabaseImpl(
    database: ExpenseDatabase
) : TransactionDatabase {
    private val dao = database.spendingDao()

    override suspend fun insert(transaction: TransactionEntity) {
        withContext(Dispatchers.IO) {
            dao.forceInsert(transaction)
        }
    }

    override fun observeSpendings(): Flow<List<TransactionEntity>> {
        return dao.observeSpending()
    }

    override suspend fun getSpendings(): List<TransactionEntity> {
        return withContext(Dispatchers.IO){
            dao.getSpendings()
        }
    }

    override suspend fun delete(transaction: TransactionEntity) {
        withContext(Dispatchers.IO) {
            dao.delete(transaction)
        }
    }

    override suspend fun deleteAll() {
        withContext(Dispatchers.IO) {
            dao.deleteAll()
        }
    }

    override fun observeSpendingsDetails(): Flow<List<TransactionDetails>> {
        return dao.observeSpendingDetails()
    }
}