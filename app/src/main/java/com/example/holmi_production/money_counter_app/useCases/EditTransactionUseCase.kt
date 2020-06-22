package com.example.holmi_production.money_counter_app.useCases

interface EditTransactionUseCase {

    suspend fun deleteTransaction(transactionId:String)

}