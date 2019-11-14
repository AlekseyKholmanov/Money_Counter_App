package com.example.holmi_production.money_counter_app.interactor

import android.util.Log
import com.example.holmi_production.money_counter_app.extensions.async
import com.example.holmi_production.money_counter_app.model.entity.Balance
import com.example.holmi_production.money_counter_app.storage.BalanceRepository
import com.example.holmi_production.money_counter_app.storage.PeriodsRepository
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.rxkotlin.Flowables
import io.reactivex.rxkotlin.Singles
import org.joda.time.DateTime
import javax.inject.Inject

class BalanceInteractor @Inject constructor(
    private val spendingInteractor: SpendingInteractor,
    private val balanceRepository: BalanceRepository,
    private val periodsRepository: PeriodsRepository){

    fun insert(){
        spendingInteractor.getIncomesAndSpendings()
            .map {
                val income =  it.first.sumByDouble { spending -> spending.sum }
                val spending = it.second.sumByDouble { spending -> spending.sum }
                return@map income - spending
            }
            .doAfterSuccess { sum ->
                val balance = Balance(DateTime.now().withTimeAtStartOfDay(), sum)
                balanceRepository.insert(balance).async().subscribe()
            }
            .async().subscribe()
    }
    fun observeBalances(): Flowable<List<Balance>> {
        return Flowables.combineLatest(balanceRepository.observeBalances(), periodsRepository.observePeriod())
            .map { (balances, period) ->
                if (period.leftBorder == period.rightBorder) {
                    balances.filter { it.id == period.leftBorder }
                } else {
                    balances.filter { it.id >= period.leftBorder && it.id <= period.rightBorder }
                }
            }
    }

}