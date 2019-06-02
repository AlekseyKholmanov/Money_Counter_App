package com.example.holmi_production.money_counter_app.main

import com.arellomobile.mvp.MvpView

interface MainFragmnetView:MvpView{
    fun updateMoney(money:String)
    fun updateSpentSum(sum:String)
    fun showIncomeSum(money:String)
}