package com.example.holmi_production.money_counter_app.useCases.impl

import com.example.holmi_production.money_counter_app.model.TransactionDetails
import com.example.holmi_production.money_counter_app.model.entity.TransactionEntity
import com.example.holmi_production.money_counter_app.storage.TransactionDatabase
import com.example.holmi_production.money_counter_app.useCases.GetTransactionUseCase
import kotlinx.coroutines.flow.Flow

class GetTransactionUseCaseImpl(
    private val transactionDatabase: TransactionDatabase
):GetTransactionUseCase {

    override fun observeTransactions(): Flow<List<TransactionEntity>> {
        return transactionDatabase.observeTransactions()
    }

    override fun observeTransactionsDetails(): Flow<List<TransactionDetails>> {
        return transactionDatabase.observeTransactionsDetails()
    }

}