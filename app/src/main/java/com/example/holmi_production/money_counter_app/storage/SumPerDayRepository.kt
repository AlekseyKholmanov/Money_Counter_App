package com.example.holmi_production.money_counter_app.storage

import com.example.holmi_production.money_counter_app.model.entity.SumPerDayEntity
import com.example.holmi_production.money_counter_app.orm.ExpenseDatabase
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.rxkotlin.Singles
import javax.inject.Inject

class SumPerDayRepository (
    private val database: ExpenseDatabase
) {
    companion object {
        const val TODAY = "TODAY_SUM"
        const val AVERAGE = "AVERAGE_SUM"
    }

    private val dao = database.sumPerDayDao

    fun insertToday(todaySum: Double): Completable {
        val sum = SumPerDayEntity(TODAY, todaySum)
        return Completable.fromCallable { dao.insert(sum) }
    }

    fun insertAverage(averageSum: Double): Completable {
        val sum = SumPerDayEntity(AVERAGE, averageSum)
        return Completable.fromCallable { dao.insert(sum) }
    }

    fun observeToday(): Flowable<SumPerDayEntity> = dao.observeSum(TODAY).distinctUntilChanged()
    fun observeAverage(): Flowable<SumPerDayEntity> = dao.observeSum(AVERAGE).distinctUntilChanged()

    fun getToday(): Single<SumPerDayEntity> {
        return Single.fromCallable { dao.getSum(TODAY) }
    }

    fun getTodayAndAverage(): Single<Pair<SumPerDayEntity, SumPerDayEntity>> {
        return Singles.zip(getToday(), getAverage())
    }

    fun getAverage(): Single<SumPerDayEntity> {
        return Single.fromCallable { dao.getSum(AVERAGE) }
    }
}