package com.example.holmi_production.money_counter_app.storage

import com.example.holmi_production.money_counter_app.model.SumPerDay
import com.example.holmi_production.money_counter_app.orm.ExpenseDatabase
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import org.joda.time.DateTime
import javax.inject.Inject

class SumPerDayRepository @Inject constructor(
    database: ExpenseDatabase
) {
    private val dao = database.sumPerDayDao()

    fun insert(sumPerDay: SumPerDay): Completable {
        return Completable.fromCallable { dao.insert(sumPerDay) }
    }

    fun insert(sumPerDayList: List<SumPerDay>): Completable {
        return Completable.fromCallable { dao.insert(sumPerDayList) }
    }

    fun getByDate(dateTime: DateTime): Flowable<SumPerDay> = dao.getByDate(dateTime)

    fun getFromDate(dateTime: DateTime):Single<List<SumPerDay>> = dao.getFromDate(dateTime)
}