package com.example.holmi_production.money_counter_app.interactor

import com.example.holmi_production.money_counter_app.model.SpDirection
import com.example.holmi_production.money_counter_app.model.entity.SpendingDetails
import com.example.holmi_production.money_counter_app.model.entity.SpendingEntity
import com.example.holmi_production.money_counter_app.storage.CategoryDatabase
import com.example.holmi_production.money_counter_app.storage.SettingRepository
import com.example.holmi_production.money_counter_app.storage.SpendingDatabase
import com.example.holmi_production.money_counter_app.storage.SumPerDayDatabase
import com.example.holmi_production.money_counter_app.storage.impl.PeriodsDatabaseImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import org.joda.time.DateTime


class SpendingInteractor(
    private val spendingDatabase: SpendingDatabase,
    private val sumPerDayDatabase: SumPerDayDatabase,
    private val settingRepository: SettingRepository,
    private val periodsDatabase: PeriodsDatabaseImpl,
    private val categoryDatabase: CategoryDatabase
) {

    suspend fun insert(spending: SpendingEntity) {
        spendingDatabase.insert(spending)
        categoryDatabase.increaseUsageCount(spending.categoryId)
    }

    suspend fun getAll(): List<SpendingEntity> {
        return spendingDatabase.getSpendings()
    }

    fun observeSpendingDetails(): Flow<List<SpendingDetails>> {
        return spendingDatabase.observeSpendingsDetails()
    }

    fun observeWithPeriods(): Flow<List<SpendingEntity>> {
        return periodsDatabase.observePeriod()
            .combine(spendingDatabase.observeSpendings()) { periods, spendings ->
                if (periods.leftBorder == periods.rightBorder) {
                    spendings.filter { it.createdDate == periods.leftBorder }
                } else {
                    spendings.filter { it.createdDate >= periods.leftBorder && it.createdDate <= periods.rightBorder }
                }
            }
    }

    suspend fun delete(spending: SpendingEntity) {
        val endPeriodDays = settingRepository.getDaysToEndPeriod()
        val today = sumPerDayDatabase.getToday()
        val average = sumPerDayDatabase.getAverage()
        when (spending.isSpending) {
            SpDirection.INCOME -> {
                val deltaAverage = spending.sum / endPeriodDays
                sumPerDayDatabase.insertToday(today.sum - deltaAverage)
                sumPerDayDatabase.insertAverage(average.sum - deltaAverage)
                spendingDatabase.delete(spending)
            }
            else -> {
                when (spending.createdDate.dayOfYear()) {
                    DateTime.now().dayOfYear() -> {

                        sumPerDayDatabase.insertToday(today.inc(spending.sum).sum)
                        spendingDatabase.delete(spending)
                    }
                    else -> {
                        val deltaAverage = spending.sum / endPeriodDays
                        sumPerDayDatabase.insertToday(today.sum + deltaAverage)
                        sumPerDayDatabase.insertAverage(average.sum + deltaAverage)
                        spendingDatabase.delete(spending)
                    }
                }
            }
        }
    }

    suspend fun deleteAll() {
        return spendingDatabase.deleteAll()
    }

}