package com.example.holmi_production.money_counter_app.firstLaunch

import com.arellomobile.mvp.MvpView

interface FirstLaunchView :MvpView{
    fun displayDateActivity()
    fun showDate(pickedDate:String, difference:String)
    fun showSumPerDay(sumPerDay:String)

}