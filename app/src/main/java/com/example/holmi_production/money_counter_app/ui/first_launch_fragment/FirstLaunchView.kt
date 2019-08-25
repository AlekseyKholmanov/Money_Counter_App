package com.example.holmi_production.money_counter_app.ui.first_launch_fragment

import com.arellomobile.mvp.MvpView

interface FirstLaunchView :MvpView{
    fun showDate(pickedDate:String, difference:String)
    fun showSumPerDay(sumPerDay:String)
    fun showMainScreen()
}