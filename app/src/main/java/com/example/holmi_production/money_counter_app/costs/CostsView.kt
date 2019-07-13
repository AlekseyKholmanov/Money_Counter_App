package com.example.holmi_production.money_counter_app.costs

import com.arellomobile.mvp.MvpView
import com.example.holmi_production.money_counter_app.model.ListItem

interface CostsView :MvpView{
    fun onError(error:Throwable)
    fun showSpending(spending: List<ListItem>)
    fun updateList()
}