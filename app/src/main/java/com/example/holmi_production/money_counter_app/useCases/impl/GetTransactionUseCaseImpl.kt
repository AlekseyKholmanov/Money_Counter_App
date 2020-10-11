package com.example.holmi_production.money_counter_app.useCases.impl

import com.example.holmi_production.money_counter_app.model.dto.TransactionDetailsDTO
import com.example.holmi_production.money_counter_app.model.entity.TransactionEntity
import com.example.holmi_production.money_counter_app.storage.db.TransactionDatabase
import com.example.holmi_production.money_counter_app.useCases.GetTransactionUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class GetTransactionUseCaseImpl(
    private val transactionDatabase: TransactionDatabase
) : GetTransactionUseCase {

    override fun observeTransactions(): Flow<List<TransactionEntity>> {
        return transactionDatabase.observeTransactions()
    }

    override fun observeDetailsByAccountId(accountId: String): Flow<List<TransactionDetailsDTO>> {
        return transactionDatabase.observeTransactionDetailsByAccountId(accountId)
    }

    override fun observeDetails(): Flow<List<TransactionDetailsDTO>> {
        return transactionDatabase.observeTransactionDetails()
    }

    override fun observeTransactionCount(): Flow<Int>  {
        return transactionDatabase.observeTransactionCount()
    }
}

