package com.example.holmi_production.money_counter_app.interactor

import android.util.Log
import com.example.holmi_production.money_counter_app.extensions.async
import com.example.holmi_production.money_counter_app.extensions.complete
import com.example.holmi_production.money_counter_app.model.SpDirection
import com.example.holmi_production.money_counter_app.model.entity.SpendingDetails
import com.example.holmi_production.money_counter_app.model.entity.SpendingEntity
import com.example.holmi_production.money_counter_app.storage.impl.PeriodsDatabaseImpl
import com.example.holmi_production.money_counter_app.storage.SettingRepository
import com.example.holmi_production.money_counter_app.storage.impl.SpendingDatabaseImpl
import com.example.holmi_production.money_counter_app.storage.impl.SumPerDayDatabaseImpl
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.Flowables
import org.joda.time.DateTime


class SpendingInteractor (
    private val spendingDatabase: SpendingDatabaseImpl,
    private val sumPerDayDatabase: SumPerDayDatabaseImpl,
    private val settingRepository: SettingRepository,
    private val periodsDatabase: PeriodsDatabaseImpl,
    private val categoryInteractor: CategoryInteractor
) {

    fun insert(spending: SpendingEntity): Completable {
        return spendingDatabase.insert(spending)
            .doOnComplete {
                categoryInteractor.getCategory(spending.categoryId).doOnSuccess {
                    categoryInteractor.insert(it.copy(usageCount = it.usageCount.plus(1)))
                        .subscribe()
                }.subscribe()
            }
    }

    fun getIncomesAndSpendings(): Single<Pair<List<SpendingEntity>, List<SpendingEntity>>> {
        return getAll()
            .async()
            .map { list ->
                val income = list.toMutableList().filter { it.isSpending == SpDirection.INCOME }
                val spending = list.toMutableList().filter { it.isSpending == SpDirection.SPENDING }
                Pair(income, spending)
            }
    }

    fun getAll(): Single<List<SpendingEntity>> {
        return spendingDatabase.getAll()
    }

    fun observeSpendingWithType(): Flowable<List<SpendingDetails>> {
        return spendingDatabase.observeSpendingsDetails()
    }

    fun observePeriods(): Flowable<List<SpendingEntity>> {
        return Flowables.combineLatest(
            periodsDatabase.observePeriod(),
            spendingDatabase.observeSpending()
        )
            .map { (period, list) ->
                Log.d("M_SpendingInteractor", "listcount ${list.count()}")
                Log.d(
                    "M_SpendingInteractor",
                    "left border ${period.leftBorder}  right border ${period.rightBorder}"
                )
                if (period.leftBorder == period.rightBorder) {
                    list.filter { it.createdDate == period.leftBorder }
                } else {
                    list.filter { it.createdDate >= period.leftBorder && it.createdDate <= period.rightBorder }
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

    fun deleteAll(): Completable {
        return spendingDatabase.deleteAll().async()
    }

}