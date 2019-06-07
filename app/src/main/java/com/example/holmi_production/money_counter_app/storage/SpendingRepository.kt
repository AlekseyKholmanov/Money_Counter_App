package com.example.holmi_production.money_counter_app.storage

import com.example.holmi_production.money_counter_app.model.Period
import com.example.holmi_production.money_counter_app.model.Spending
import com.example.holmi_production.money_counter_app.orm.ExpenseDatabase
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class SpendingRepository @Inject constructor(
    database: ExpenseDatabase
) {
    private val dao = database.spendingDao()
    private val periodDao = database.peiodDao()

    fun insert(spending: Spending): Completable {
        return Completable.fromCallable { dao.insert(spending) }
    }

    fun getAll(): Flowable<List<Spending>> {
        return dao.getAll()
    }

    fun getSpentSum(): Flowable<List<Float>> {
        return dao.getSpentSum()
    }

    fun getIncomeSum(): Flowable<List<Float>> {
        return dao.getIncomeSum()
    }

    fun delete(spending: Spending): Completable {
        return Completable.fromCallable { dao.delete(spending) }
    }

    fun getPeiods():Single<List<Period>>{
        return periodDao.getAll()
    }
    fun insertPeriods(periods:List<Period>):Completable{
        return Completable.fromCallable { periodDao.insert(periods) }
    }
    fun insertPeriod(period:Period):Completable{
        return Completable.fromCallable { periodDao.insert(period) }
    }



}