package com.example.holmi_production.money_counter_app.main

import com.arellomobile.mvp.MvpView

interface MainFragmnetView:MvpView{
    fun showMoney(money:String)
    fun showSpentSum(sum:String)
    fun showIncomeSum(money:String)
    fun showSumPerDay(money:String)
    fun showDaysLeft(days:String)
    fun showAverageSum(sum:String, isDisplayed:Boolean)
    fun showSnack(message: String)
}