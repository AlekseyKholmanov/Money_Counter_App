package com.example.holmi_production.money_counter_app.interactor

import com.example.holmi_production.money_counter_app.model.TransactionDetails
import com.example.holmi_production.money_counter_app.model.entity.TransactionEntity
import com.example.holmi_production.money_counter_app.storage.CategoryDatabase
import com.example.holmi_production.money_counter_app.storage.AppPreference
import com.example.holmi_production.money_counter_app.storage.SumPerDayDatabase
import com.example.holmi_production.money_counter_app.storage.TransactionDatabase
import com.example.holmi_production.money_counter_app.storage.impl.PeriodsDatabaseImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine


class SpendingInteractor(
    private val transactionDatabase: TransactionDatabase,
    private val sumPerDayDatabase: SumPerDayDatabase,
    private val appPreference: AppPreference,
    private val periodsDatabase: PeriodsDatabaseImpl,
    private val categoryDatabase: CategoryDatabase
) {

    suspend fun insert(transaction: TransactionEntity) {
        transactionDatabase.insert(transaction)
        if (transaction.categoryId != null) {
            categoryDatabase.increaseUsageCount(transaction.categoryId)
        }
    }

    suspend fun getAll(): List<TransactionEntity> {
        return transactionDatabase.getTransactions()
    }

    fun observeSpendingDetails(): Flow<List<TransactionDetails>> {
        return transactionDatabase.observeTransactionsDetails()
    }

    fun observeWithPeriods(): Flow<List<TransactionEntity>> {
        return periodsDatabase.observePeriod()
            .combine(transactionDatabase.observeTransactions()) { periods, spendings ->
                if (periods.from == periods.to) {
                    spendings.filter { it.createdDate == periods.from }
                } else {
                    spendings.filter { it.createdDate >= periods.from && it.createdDate <= periods.to }
                }
            }
    }

    //TODO it can have different type of account
    suspend fun delete(transaction: TransactionEntity) {
//        val endPeriodDays = settingRepository.getDaysToEndPeriod()
//        val today = sumPerDayDatabase.getToday()
//        val average = sumPerDayDatabase.getAverage()
//        when (transaction.sum) {
//            SpDirection.INCOME -> {
//                val deltaAverage = transaction.sum / endPeriodDays
//                sumPerDayDatabase.insertToday(today.sum - deltaAverage)
//                sumPerDayDatabase.insertAverage(average.sum - deltaAverage)
//                transactionDatabase.delete(transaction)
//            }
//            else -> {
//                when (transaction.createdDate.dayOfYear()) {
//                    DateTime.now().dayOfYear() -> {
//
//                        sumPerDayDatabase.insertToday(today.inc(transaction.sum).sum)
//                        transactionDatabase.delete(transaction)
//                    }
//                    else -> {
//                        val deltaAverage = transaction.sum / endPeriodDays
//                        sumPerDayDatabase.insertToday(today.sum + deltaAverage)
//                        sumPerDayDatabase.insertAverage(average.sum + deltaAverage)
//                        transactionDatabase.delete(transaction)
//                    }
//                }
//            }
//        }
    }

    suspend fun deleteAll() {
        return transactionDatabase.deleteAll()
    }

}