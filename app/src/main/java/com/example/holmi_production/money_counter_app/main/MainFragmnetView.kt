package com.example.holmi_production.money_counter_app.main

import com.arellomobile.mvp.MvpView

interface MainFragmnetView:MvpView{
    fun showMoney(money:String)
}