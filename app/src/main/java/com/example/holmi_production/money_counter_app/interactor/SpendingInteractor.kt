package com.example.holmi_production.money_counter_app.interactor

import com.example.holmi_production.money_counter_app.model.TransactionDetails
import com.example.holmi_production.money_counter_app.model.entity.TransactionEntity
import com.example.holmi_production.money_counter_app.model.enums.SpDirection
import com.example.holmi_production.money_counter_app.storage.CategoryDatabase
import com.example.holmi_production.money_counter_app.storage.SettingRepository
import com.example.holmi_production.money_counter_app.storage.SumPerDayDatabase
import com.example.holmi_production.money_counter_app.storage.TransactionDatabase
import com.example.holmi_production.money_counter_app.storage.impl.PeriodsDatabaseImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import org.joda.time.DateTime


class SpendingInteractor(
    private val transactionDatabase: TransactionDatabase,
    private val sumPerDayDatabase: SumPerDayDatabase,
    private val settingRepository: SettingRepository,
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
        return transactionDatabase.getSpendings()
    }

    fun observeSpendingDetails(): Flow<List<TransactionDetails>> {
        return transactionDatabase.observeSpendingsDetails()
    }

    fun observeWithPeriods(): Flow<List<TransactionEntity>> {
        return periodsDatabase.observePeriod()
            .combine(transactionDatabase.observeSpendings()) { periods, spendings ->
                if (periods.leftBorder == periods.rightBorder) {
                    spendings.filter { it.createdDate == periods.leftBorder }
                } else {
                    spendings.filter { it.createdDate >= periods.leftBorder && it.createdDate <= periods.rightBorder }
                }
            }
    }

    suspend fun delete(transaction: TransactionEntity) {
        val endPeriodDays = settingRepository.getDaysToEndPeriod()
        val today = sumPerDayDatabase.getToday()
        val average = sumPerDayDatabase.getAverage()
        when (transaction.sum) {
            SpDirection.INCOME -> {
                val deltaAverage = transaction.sum / endPeriodDays
                sumPerDayDatabase.insertToday(today.sum - deltaAverage)
                sumPerDayDatabase.insertAverage(average.sum - deltaAverage)
                transactionDatabase.delete(transaction)
            }
            else -> {
                when (transaction.createdDate.dayOfYear()) {
                    DateTime.now().dayOfYear() -> {

                        sumPerDayDatabase.insertToday(today.inc(transaction.sum).sum)
                        transactionDatabase.delete(transaction)
                    }
                    else -> {
                        val deltaAverage = transaction.sum / endPeriodDays
                        sumPerDayDatabase.insertToday(today.sum + deltaAverage)
                        sumPerDayDatabase.insertAverage(average.sum + deltaAverage)
                        transactionDatabase.delete(transaction)
                    }
                }
            }
        }
    }

    suspend fun deleteAll() {
        return transactionDatabase.deleteAll()
    }

}