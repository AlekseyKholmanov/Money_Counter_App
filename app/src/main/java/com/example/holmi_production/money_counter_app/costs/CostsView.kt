package com.example.holmi_production.money_counter_app.costs

import com.arellomobile.mvp.MvpView

interface CostsView :MvpView{
    fun onError(error:Throwable)
    fun showSpending(spending: MutableList<ListItem>)
}