package com.example.holmi_production.money_counter_app.storage.impl

import com.example.holmi_production.money_counter_app.model.TransactionDetails
import com.example.holmi_production.money_counter_app.model.entity.TransactionEntity
import com.example.holmi_production.money_counter_app.orm.TransactionDao
import com.example.holmi_production.money_counter_app.storage.TransactionDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext


class TransactionDatabaseImpl(
    private val dao: TransactionDao
) : TransactionDatabase {

    override suspend fun insert(transaction: TransactionEntity) {
        withContext(Dispatchers.IO) {
            dao.forceInsert(transaction)
        }
    }

    override fun observeTransactions(): Flow<List<TransactionEntity>> {
        return dao.observeTransactions()
    }

    override suspend fun getTransactions(): List<TransactionEntity> {
        return withContext(Dispatchers.IO) {
            dao.getTransactions()
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

    override suspend fun markDeleted(transactionId: String) = withContext(Dispatchers.IO){
        dao.markDeleted(transactionId)
    }

    override fun observeTransactionsDetails(): Flow<List<TransactionDetails>> {
        return dao.observeTransactionDetails()
    }
}