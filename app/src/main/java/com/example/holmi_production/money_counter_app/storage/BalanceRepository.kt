package com.example.holmi_production.money_counter_app.storage

import com.example.holmi_production.money_counter_app.model.entity.Balance
import com.example.holmi_production.money_counter_app.model.entity.FilterPeriods
import com.example.holmi_production.money_counter_app.orm.ExpenseDatabase
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import org.joda.time.DateTime
import javax.inject.Inject

class BalanceRepository @Inject constructor(
    database: ExpenseDatabase
) {
    private val dao = database.balanceDao

    fun insert(balance: Balance): Completable {
        return Completable.fromCallable { dao.insert(balance) }
    }

    fun getBalance(id:DateTime): Single<Balance> {
        return Single.fromCallable { dao.getBalance(id)}
    }

    fun observeBalances(): Flowable<List<Balance>> {
        return dao.getBalances().distinctUntilChanged()
    }
}