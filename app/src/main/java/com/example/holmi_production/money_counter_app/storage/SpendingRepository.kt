package com.example.holmi_production.money_counter_app.storage

import com.example.holmi_production.money_counter_app.model.Spending
import com.example.holmi_production.money_counter_app.orm.MoneyCounterDb
import io.reactivex.Completable
import javax.inject.Inject

class SpendingRepository @Inject constructor(
    database: MoneyCounterDb
) {
    private val dao = database.spendingDao()

    fun insert(spending: Spending): Completable {
        return Completable.fromCallable { dao.insert(spending) }
    }
}