package com.example.holmi_production.money_counter_app.storage

import com.example.holmi_production.money_counter_app.model.entity.Spending
import com.example.holmi_production.money_counter_app.model.entity.SpendingWithCategory
import com.example.holmi_production.money_counter_app.orm.ExpenseDatabase
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import org.joda.time.DateTime
import javax.inject.Inject

class SpendingRepository @Inject constructor(
    database: ExpenseDatabase
) {
    private val dao = database.spendingDao

    fun insert(spending: Spending): Completable {
        return Completable.fromCallable { dao.insert(spending) }
    }

    fun getAll(): Single<List<Spending>> {
        return Single.fromCallable { dao.getSpendings() }
    }

    fun observeSpending(): Flowable<List<Spending>> {
        return dao.observeSpending()
    }

    fun observeSpendingWithCategory(): Flowable<List<SpendingWithCategory>> {
        return Flowable.fromCallable { dao.observeSpendingWithCategory() }
    }

    fun getSpendingWitCategory(id:DateTime): Single<SpendingWithCategory> {
        return Single.fromCallable { dao.getSpendingWithCategory(id)}
    }

    fun delete(spending: Spending): Completable {
        return Completable.fromCallable { dao.delete(spending) }
    }

    fun deleteAll(): Completable {
        return Completable.fromCallable { dao.deleteAll() }

    }
}