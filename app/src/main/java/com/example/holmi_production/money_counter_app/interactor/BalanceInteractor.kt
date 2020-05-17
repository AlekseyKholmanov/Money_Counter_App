package com.example.holmi_production.money_counter_app.interactor

import com.example.holmi_production.money_counter_app.extensions.async
import com.example.holmi_production.money_counter_app.model.entity.BalanceEntity
import com.example.holmi_production.money_counter_app.storage.BalanceRepository
import com.example.holmi_production.money_counter_app.storage.PeriodsRepository
import io.reactivex.Flowable
import io.reactivex.rxkotlin.Flowables
import org.joda.time.DateTime


class BalanceInteractor(
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
                val balance = BalanceEntity(DateTime.now().withTimeAtStartOfDay(), sum)
                balanceRepository.insert(balance).async().subscribe()
            }
            .async().subscribe()
    }

    fun insert(balance: BalanceEntity){
        balanceRepository.insert(balance)
            .async().subscribe()
    }
    fun observeBalances(): Flowable<List<BalanceEntity>> {
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