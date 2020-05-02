package com.example.holmi_production.money_counter_app.ui.presenters

import moxy.MvpView

interface FirstLaunchView :MvpView{
    fun showDate(pickedDate:String, difference:String)
    fun showSumPerDay(sumPerDay:String)
    fun showMainScreen()
}