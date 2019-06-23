package com.example.holmi_production.money_counter_app.storage

import com.example.holmi_production.money_counter_app.model.SumPerDay
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
        val today = "TODAY_SUM"
        val average = "AVERAGE_SUM"
    }

    private val dao = database.sumPerDayDao

    fun insertToday(todaySum: Double): Completable {
        val sum = SumPerDay(today, todaySum)
        return Completable.fromCallable { dao.insert(sum) }
    }

    fun insertAverage(averageSum: Double): Completable {
        val sum = SumPerDay(average, averageSum)
        return Completable.fromCallable { dao.insert(sum) }
    }

    fun observeToday(): Flowable<SumPerDay> = dao.observeSum(today)
    fun observeAverage(): Flowable<SumPerDay> = dao.observeSum(average)

    fun getToday(): Single<SumPerDay> {
        return Single.fromCallable { dao.getSum(today) }
    }

    fun getBoth(): Single<Pair<SumPerDay, SumPerDay>> {
        return Singles.zip(getToday(),getAverage())
    }

    fun getAverage(): Single<SumPerDay> {
        return Single.fromCallable { dao.getSum(average) }
    }
}