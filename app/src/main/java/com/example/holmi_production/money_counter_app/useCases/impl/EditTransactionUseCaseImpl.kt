package com.example.holmi_production.money_counter_app.useCases.impl

import com.example.holmi_production.money_counter_app.storage.db.TransactionDatabase
import com.example.holmi_production.money_counter_app.useCases.EditTransactionUseCase

class EditTransactionUseCaseImpl(
    private val transactionDatabase: TransactionDatabase
) : EditTransactionUseCase {

    override suspend fun deleteTransaction(transactionId: String) {
        transactionDatabase.markDeleted(transactionId)
    }

}