package com.example.holmi_production.money_counter_app.storage

import com.example.holmi_production.money_counter_app.model.SumPerDay
import com.example.holmi_production.money_counter_app.orm.ExpenseDatabase
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import org.joda.time.DateTime
import java.util.*
import javax.inject.Inject

class SumPerDayRepository @Inject constructor(
    database: ExpenseDatabase
) {
    private val dao = database.sumPerDayDao

    fun insert(sumPerDay: SumPerDay): Completable {
        return Completable.fromCallable { dao.insert(sumPerDay) }
    }

    fun insert(sumPerDayList: Collection<SumPerDay>): Completable {
        return Completable.fromCallable { dao.insert(sumPerDayList) }
    }

    fun observeSumPerDay(): Flowable<List<SumPerDay>> = dao.observeSumPerDay()

    fun getOnDate(dateTime: DateTime): Single<SumPerDay> {
        return Single.fromCallable { dao.getOnDate(dateTime) }
    }

    fun getFromDate(dateTime: DateTime): Single<List<SumPerDay>> {
        return Single.fromCallable { dao.getPeriodFrom(dateTime) }
    }
}