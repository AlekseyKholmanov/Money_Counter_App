package com.example.holmi_production.money_counter_app.useCases.impl

import com.example.holmi_production.money_counter_app.model.entity.TransactionEntity
import com.example.holmi_production.money_counter_app.storage.CategoryDatabase
import com.example.holmi_production.money_counter_app.storage.TransactionDatabase
import com.example.holmi_production.money_counter_app.useCases.SaveTransactionUseCase

class SaveTransactionUseCaseImpl(
    private val transactionDatabase: TransactionDatabase,
    private val categoryDatabase: CategoryDatabase
) : SaveTransactionUseCase {
    override suspend fun save(transaction: TransactionEntity) {
        transactionDatabase.insert(transaction)
        if (transaction.categoryId != null) {
            categoryDatabase.increaseUsageCount(transaction.categoryId)
        }
    }
}