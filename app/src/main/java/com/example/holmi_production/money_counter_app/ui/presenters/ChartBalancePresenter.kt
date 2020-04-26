package com.example.holmi_production.money_counter_app.ui.presenters

import com.arellomobile.mvp.InjectViewState
import com.example.holmi_production.money_counter_app.extensions.async
import com.example.holmi_production.money_counter_app.interactor.BalanceInteractor
import com.example.holmi_production.money_counter_app.model.entity.BalanceEntity
import com.example.holmi_production.money_counter_app.mvp.BasePresenter
import org.joda.time.DateTime
import javax.inject.Inject

@InjectViewState
class ChartBalancePresenter @Inject constructor(private val balanceInteractor: BalanceInteractor) :
    BasePresenter<ChartBalanceView>() {


    fun observeBalances() {
        balanceInteractor.observeBalances()
            .async()
            .subscribe({
                if (it.count() < 2) {
                    viewState.showError()
                } else {
                    viewState.showChart(it)
                }
            }, {
                viewState.showError()
            })
            .keep()
    }
    private fun getTestObj():List<BalanceEntity>{
        val balances = arrayListOf<BalanceEntity>()
        val Date = DateTime.now().minusDays(180)
        var amount = 5000.0
        for (i in 0..18){
            amount += 100
            if(i%21 == 0) amount -= 1400
            val balance = BalanceEntity(Date.plusDays(i), amount = amount)
            balances.add(balance)
        }
        return balances
    }
}