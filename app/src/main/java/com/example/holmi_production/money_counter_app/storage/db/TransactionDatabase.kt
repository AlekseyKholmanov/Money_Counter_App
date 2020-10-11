package com.example.holmi_production.money_counter_app.storage.db

import com.example.holmi_production.money_counter_app.model.dto.TransactionDetailsDTO
import com.example.holmi_production.money_counter_app.model.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
interface TransactionDatabase {

    suspend fun insert(transaction: TransactionEntity)

    suspend fun getTransactions(): List<TransactionEntity>

    suspend fun delete(transaction: TransactionEntity)

    suspend fun deleteAll()

    suspend fun markDeleted(transactionId: String)

    //region observe

    fun observeTransactions(): Flow<List<TransactionEntity>>

    fun observeTransactionsWithPeriod(): Flow<List<TransactionEntity>>

    fun observeTransactionDetailsByAccountId(accountId: String): Flow<List<TransactionDetailsDTO>>

    fun observeTransactionDetails(): Flow<List<TransactionDetailsDTO>>

    fun observeTransactionCount(): Flow<Int>

    //endregion
}