package com.example.holmi_production.money_counter_app.ui.viewModels

import androidx.lifecycle.ViewModel
import com.example.holmi_production.money_counter_app.extensions.async
import com.example.holmi_production.money_counter_app.interactor.BalanceInteractor
import com.example.holmi_production.money_counter_app.model.entity.BalanceEntity

import org.joda.time.DateTime



class ChartBalanceViewModel(private val balanceInteractor: BalanceInteractor) :
    ViewModel() {


    fun observeBalances() {
//        balanceInteractor.observeBalances()
//            .async()
//            .subscribe({
//                if (it.count() < 2) {
//                    viewState.showError()
//                } else {
//                    viewState.showChart(it)
//                }
//            }, {
//                viewState.showError()
//            })
//            .keep()
    }

    private fun getTestObj(): List<BalanceEntity> {
        val balances = arrayListOf<BalanceEntity>()
        val Date = DateTime.now().minusDays(180)
        var amount = 5000.0
        for (i in 0..18) {
            amount += 100
            if (i % 21 == 0) amount -= 1400
            val balance = BalanceEntity(Date.plusDays(i), amount = amount)
            balances.add(balance)
        }
        return balances
    }
}