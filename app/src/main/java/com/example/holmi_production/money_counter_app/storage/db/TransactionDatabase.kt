package com.example.holmi_production.money_counter_app.storage.db

import com.example.holmi_production.money_counter_app.model.TransactionDetails
import com.example.holmi_production.money_counter_app.model.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
interface TransactionDatabase {

    suspend fun insert(transaction: TransactionEntity)

    fun observeTransactions(): Flow<List<TransactionEntity>>

    suspend fun getTransactions(): List<TransactionEntity>

    suspend fun delete(transaction: TransactionEntity)

    suspend fun deleteAll()

    suspend fun markDeleted(transactionId:String)

    fun observeTransactionsDetails(): Flow<List<TransactionDetails>>
}