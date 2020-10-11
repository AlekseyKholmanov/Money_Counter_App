package com.example.holmi_production.money_counter_app.useCases

import com.example.holmi_production.money_counter_app.model.dto.TransactionDetailsDTO
import com.example.holmi_production.money_counter_app.model.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

interface GetTransactionUseCase {

    fun observeTransactions(): Flow<List<TransactionEntity>>

    fun observeDetailsByAccountId(accountId: String): Flow<List<TransactionDetailsDTO>>

    fun observeDetails(): Flow<List<TransactionDetailsDTO>>

    fun observeTransactionCount(): Flow<Int>
}