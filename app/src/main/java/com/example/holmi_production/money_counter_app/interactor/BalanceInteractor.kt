package com.example.holmi_production.money_counter_app.interactor

import com.example.holmi_production.money_counter_app.model.entity.BalanceEntity
import com.example.holmi_production.money_counter_app.storage.impl.BalanceDatabaseImpl
import com.example.holmi_production.money_counter_app.storage.impl.PeriodsDatabaseImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine


class BalanceInteractor(
    private val spendingInteractor: SpendingInteractor,
    private val balanceDatabase: BalanceDatabaseImpl,
    private val periodsDatabase: PeriodsDatabaseImpl
) {


    suspend fun insert(balance: BalanceEntity) {
        balanceDatabase.insert(balance)
    }

    fun observeBalances(): Flow<List<BalanceEntity>> {
        return balanceDatabase.observeBalances()
            .combine(periodsDatabase.observePeriod()) { balances, period ->
                if (period.from == period.to) {
                    balances.filter { it.id == period.from }
                } else {
                    balances.filter { it.id >= period.from && it.id <= period.to }
                }
            }
    }

}