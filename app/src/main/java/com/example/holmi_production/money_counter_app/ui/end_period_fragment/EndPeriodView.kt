package com.example.holmi_production.money_counter_app.ui.end_period_fragment

import com.arellomobile.mvp.MvpView

interface EndPeriodView :MvpView {
    fun goToMain()
    fun showDatePeriod(start:String, end:String)
    fun showLeftSum(sum:String)
    fun showSpendedSum(sum:String)
    fun ShowAverageSumForPeriod(sum:String)
}