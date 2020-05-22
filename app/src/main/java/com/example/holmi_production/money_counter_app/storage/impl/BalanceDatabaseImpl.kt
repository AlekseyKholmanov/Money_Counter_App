package com.example.holmi_production.money_counter_app.storage.impl

import com.example.holmi_production.money_counter_app.model.entity.BalanceEntity
import com.example.holmi_production.money_counter_app.orm.ExpenseDatabase
import com.example.holmi_production.money_counter_app.storage.BalanceDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.withContext
import org.joda.time.DateTime


class BalanceDatabaseImpl(
    database: ExpenseDatabase
) : BalanceDatabase {
    private val dao = database.balanceDao()

    override suspend fun insert(balance: BalanceEntity) {
        return withContext(Dispatchers.IO) { dao.insert(balance) }
    }

    override suspend fun getBalaceByDate(date: DateTime): BalanceEntity {
        return withContext(Dispatchers.IO) {
            dao.getBalance(date)
        }
    }

    override fun observeBalances(): Flow<List<BalanceEntity>> {
        return dao.getBalances().distinctUntilChanged()
    }
}