package com.example.holmi_production.money_counter_app.ui.charts_fragments.balance

import com.arellomobile.mvp.MvpView
import com.example.holmi_production.money_counter_app.model.entity.Balance

interface BalanceView : MvpView {
    fun showChart(balances:List<Balance>)
    fun showError()
}