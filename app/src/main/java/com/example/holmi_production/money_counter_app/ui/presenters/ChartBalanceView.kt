package com.example.holmi_production.money_counter_app.ui.presenters

import com.arellomobile.mvp.MvpView
import com.example.holmi_production.money_counter_app.model.entity.Balance

interface ChartBalanceView : MvpView {
    fun showChart(balances:List<Balance>)
    fun showError()
}