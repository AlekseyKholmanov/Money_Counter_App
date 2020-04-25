package com.example.holmi_production.money_counter_app.ui.presenters

import com.arellomobile.mvp.MvpView
import com.example.holmi_production.money_counter_app.model.entity.Spending
import org.joda.time.DateTime

interface StackedView:MvpView {
    fun showGraph(list: Map<DateTime, List<Spending>>)
    fun showError()
}