package com.example.holmi_production.money_counter_app.storage

import com.example.holmi_production.money_counter_app.model.entity.SpendingDetails
import com.example.holmi_production.money_counter_app.model.entity.SpendingEntity
import com.example.holmi_production.money_counter_app.orm.ExpenseDatabase
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class SpendingRepository (
    database: ExpenseDatabase
) {
    private val dao = database.spendingDao

    fun insert(spending: SpendingEntity): Completable {
        return Completable.fromCallable { dao.insert(spending) }
    }

    fun getAll(): Single<List<SpendingEntity>> {
        return Single.fromCallable { dao.getSpendings() }
    }

    fun observeSpending(): Flowable<List<SpendingEntity>> {
        return dao.observeSpending().distinctUntilChanged()
    }

    fun delete(spending: SpendingEntity): Completable {
        return Completable.fromCallable { dao.delete(spending) }
    }

    fun deleteAll(): Completable {
        return Completable.fromCallable { dao.deleteAll() }
    }

    fun observeSpendingsDetails():Flowable<List<SpendingDetails>>{
        return dao.observeSpendingDetails()
    }
}