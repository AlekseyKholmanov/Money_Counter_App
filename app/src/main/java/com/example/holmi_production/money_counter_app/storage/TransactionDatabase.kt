package com.example.holmi_production.money_counter_app.storage

import com.example.holmi_production.money_counter_app.model.TransactionDetails
import com.example.holmi_production.money_counter_app.model.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
interface TransactionDatabase {

    suspend fun insert(transaction: TransactionEntity)

    fun observeSpendings(): Flow<List<TransactionEntity>>

    suspend fun getSpendings(): List<TransactionEntity>

    suspend fun delete(transaction: TransactionEntity)

    suspend fun deleteAll()

    fun observeSpendingsDetails(): Flow<List<TransactionDetails>>
}