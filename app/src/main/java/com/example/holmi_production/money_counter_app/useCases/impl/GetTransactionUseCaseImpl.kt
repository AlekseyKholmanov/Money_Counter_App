package com.example.holmi_production.money_counter_app.useCases.impl

import com.example.holmi_production.money_counter_app.model.TransactionDetails
import com.example.holmi_production.money_counter_app.model.entity.TransactionEntity
import com.example.holmi_production.money_counter_app.storage.db.TransactionDatabase
import com.example.holmi_production.money_counter_app.useCases.GetActivePeriodUseCase
import com.example.holmi_production.money_counter_app.useCases.GetTransactionUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetTransactionUseCaseImpl(
    private val transactionDatabase: TransactionDatabase,
    private val getActivePeriodUseCase: GetActivePeriodUseCase
) : GetTransactionUseCase {

    override fun observeTransactions(): Flow<List<TransactionEntity>> {
        return transactionDatabase.observeTransactions()
    }

    override fun observeTransactionsDetails(): Flow<List<TransactionDetails>> {
        return transactionDatabase.observeTransactionsDetails()
//        return combine(
//            transactionDatabase.observeTransactionsDetails(),
//            getActivePeriodUseCase.observeLatestPeriod()
//        ) { transaction, period ->
//            transaction.filter {
//                it.transaction.createdDate <= period.to && it.transaction.createdDate >= period.from
//            }
//        }
    }

    override fun observeTransactionDetailsWithDate(): Flow<List<TransactionDetails>> {
        return transactionDatabase.observeTransactionsDetailsWithPeriod()
    }
}

