package com.example.holmi_production.money_counter_app.storage

import com.example.holmi_production.money_counter_app.model.entity.SumPerDay
import com.example.holmi_production.money_counter_app.orm.ExpenseDatabase
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.rxkotlin.Singles
import javax.inject.Inject

class SumPerDayRepository @Inject constructor(
    private val database: ExpenseDatabase
) {
    companion object {
        const val TODAY = "TODAY_SUM"
        const val AVERAGE = "AVERAGE_SUM"
    }

    private val dao = database.sumPerDayDao

    fun insertToday(todaySum: Double): Completable {
        val sum = SumPerDay(TODAY, todaySum)
        return Completable.fromCallable { dao.insert(sum) }
    }

    fun insertAverage(averageSum: Double): Completable {
        val sum = SumPerDay(AVERAGE, averageSum)
        return Completable.fromCallable { dao.insert(sum) }
    }

    fun observeToday(): Flowable<SumPerDay> = dao.observeSum(TODAY).distinctUntilChanged()
    fun observeAverage(): Flowable<SumPerDay> = dao.observeSum(AVERAGE).distinctUntilChanged()

    fun getToday(): Single<SumPerDay> {
        return Single.fromCallable { dao.getSum(TODAY) }
    }

    fun getTodayAndAverage(): Single<Pair<SumPerDay, SumPerDay>> {
        return Singles.zip(getToday(), getAverage())
    }

    fun getAverage(): Single<SumPerDay> {
        return Single.fromCallable { dao.getSum(AVERAGE) }
    }
}