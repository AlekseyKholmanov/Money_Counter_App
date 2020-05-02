package com.example.holmi_production.money_counter_app.ui.presenters.charts

import moxy.MvpView
import com.example.holmi_production.money_counter_app.model.entity.BalanceEntity

interface ChartBalanceView : MvpView {
    fun showChart(balances:List<BalanceEntity>)
    fun showError()
}