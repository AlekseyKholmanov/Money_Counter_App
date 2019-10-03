package com.example.holmi_production.money_counter_app.storage

import com.example.holmi_production.money_counter_app.model.entity.FilterPeriods
import com.example.holmi_production.money_counter_app.orm.ExpenseDatabase
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class PeriodsRepository @Inject constructor(
    database: ExpenseDatabase
) {
    companion object{
        const val key = "DATE"
    }
    private val dao = database.periodsDao

    fun insert(period: FilterPeriods): Completable {
        return Completable.fromCallable { dao.insert(period.copy(id = key)) }
    }

    fun getPeriod(): Single<FilterPeriods> {
        return Single.fromCallable { dao.getPeriod(key)}
    }

    fun observePeriod(): Flowable<FilterPeriods> {
        return dao.observePeriod(key)
    }
}