package com.example.holmi_production.money_counter_app.useCases

import com.example.holmi_production.money_counter_app.model.TransactionDetails
import com.example.holmi_production.money_counter_app.model.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

interface GetTransactionUseCase {

    fun observeTransactions(): Flow<List<TransactionEntity>>

    fun observeTransactionsDetails(): Flow<List<TransactionDetails>>

}