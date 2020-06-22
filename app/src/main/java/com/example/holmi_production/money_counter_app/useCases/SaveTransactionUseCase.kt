package com.example.holmi_production.money_counter_app.useCases

import com.example.holmi_production.money_counter_app.model.entity.TransactionEntity

interface SaveTransactionUseCase {

    suspend fun save(transaction: TransactionEntity)

}