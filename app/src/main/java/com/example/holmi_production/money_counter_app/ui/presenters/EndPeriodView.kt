package com.example.holmi_production.money_counter_app.ui.presenters

import moxy.MvpView

interface EndPeriodView :MvpView {
    fun goToMain()
    fun showDatePeriod(start:String, end:String)
    fun showLeftSum(sum:String)
    fun showSpendedSum(sum:String)
    fun ShowAverageSumForPeriod(sum:String)
}