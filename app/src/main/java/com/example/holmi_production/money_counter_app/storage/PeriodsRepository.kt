package com.example.holmi_production.money_counter_app.storage

import com.example.holmi_production.money_counter_app.model.entity.FilterPeriodEntity
import com.example.holmi_production.money_counter_app.orm.ExpenseDatabase
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
 

class PeriodsRepository (
    database: ExpenseDatabase
) {
    companion object{
        const val key = "DATE"
    }
    private val dao = database.periodsDao()

    fun insert(period: FilterPeriodEntity): Completable {
        return Completable.fromCallable { dao.insert(period.copy(id = key)) }
    }

    fun getPeriod(): Single<FilterPeriodEntity> {
        return Single.fromCallable { dao.getPeriod(key)}
    }

    fun observePeriod(): Flowable<FilterPeriodEntity> {
        return dao.observePeriod(key).distinctUntilChanged()
    }
}