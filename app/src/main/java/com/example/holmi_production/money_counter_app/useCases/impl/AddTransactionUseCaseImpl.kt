package com.example.holmi_production.money_counter_app.useCases.impl

import com.example.holmi_production.money_counter_app.model.entity.TransactionEntity
import com.example.holmi_production.money_counter_app.storage.CategoryDatabase
import com.example.holmi_production.money_counter_app.storage.TransactionDatabase
import com.example.holmi_production.money_counter_app.useCases.AddTransactionUseCase

class AddTransactionUseCaseImpl(
    private val transactionDatabase: TransactionDatabase,
    private val categoryDatabase: CategoryDatabase
) : AddTransactionUseCase {

    override suspend fun save(transaction: TransactionEntity) {
        transactionDatabase.insert(transaction)
        if (transaction.categoryId != null) {
            categoryDatabase.increaseUsageCount(transaction.categoryId)
        }
    }

}