package com.example.holmi_production.money_counter_app.interactor

import android.util.Log
import com.example.holmi_production.money_counter_app.extensions.async
import com.example.holmi_production.money_counter_app.extensions.complete
import com.example.holmi_production.money_counter_app.model.SpDirection
import com.example.holmi_production.money_counter_app.model.entity.SpendingDetails
import com.example.holmi_production.money_counter_app.model.entity.SpendingEntity
import com.example.holmi_production.money_counter_app.storage.CategoryDatabase
import com.example.holmi_production.money_counter_app.storage.impl.PeriodsDatabaseImpl
import com.example.holmi_production.money_counter_app.storage.SettingRepository
import com.example.holmi_production.money_counter_app.storage.SpendingDatabase
import com.example.holmi_production.money_counter_app.storage.SumPerDayDatabase
import com.example.holmi_production.money_counter_app.storage.impl.SpendingDatabaseImpl
import com.example.holmi_production.money_counter_app.storage.impl.SumPerDayDatabaseImpl
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.Flowables
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import org.joda.time.DateTime


class SpendingInteractor (
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
            return periodsDatabase.observePeriod().combine(spendingDatabase.observeSpendings()){
                periods, spendings ->
                if (periods.leftBorder == periods.rightBorder) {
                    spendings.filter { it.createdDate == periods.leftBorder }
                } else {
                    spendings.filter { it.createdDate >= periods.leftBorder && it.createdDate <= periods.rightBorder }
                }
            }
    }

    fun delete(spending: SpendingEntity): Single<Disposable> {
        val endPeriodDays = settingRepository.getDaysToEndPeriod()
        when (spending.isSpending) {
            SpDirection.INCOME -> {
                return sumPerDayDatabase.getTodayAndAverage()
                    .async()
                    .map { sums ->
                        val today = sums.first.sum
                        val average = sums.second.sum
                        val deltaAverage = spending.sum / endPeriodDays
                        sumPerDayDatabase.insertToday(today - deltaAverage).complete()
                        sumPerDayDatabase.insertAverage(average - deltaAverage).complete()
                    }
                    .also {
                        spendingDatabase.delete(spending).complete()
                    }
            }
            else -> {
                when (spending.createdDate.dayOfYear()) {
                    DateTime.now().dayOfYear() -> {
                        return sumPerDayDatabase.getToday()
                            .async()
                            .doOnError { t -> Log.d("qwerty", t.toString()) }
                            .map {
                                sumPerDayDatabase.insertToday(it.inc(spending.sum).sum).complete()
                            }
                            .also {
                                spendingDatabase.delete(spending).complete()
                            }
                    }
                    else -> {
                        return sumPerDayDatabase.getTodayAndAverage()
                            .async()
                            .map { sums ->
                                val today = sums.first.sum
                                val average = sums.second.sum
                                val deltaAverage = spending.sum / endPeriodDays
                                sumPerDayDatabase.insertToday(today + deltaAverage).complete()
                                sumPerDayDatabase.insertAverage(average + deltaAverage).complete()
                            }
                            .also {
                                spendingDatabase.delete(spending).complete()
                            }
                    }
                }
            }
        }
    }

    suspend fun deleteAll() {
        return spendingDatabase.deleteAll()
    }

}