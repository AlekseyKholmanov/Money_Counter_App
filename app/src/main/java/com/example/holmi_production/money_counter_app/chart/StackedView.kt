package com.example.holmi_production.money_counter_app.chart

import com.arellomobile.mvp.MvpView
import com.example.holmi_production.money_counter_app.model.Spending
import org.joda.time.DateTime

interface StackedView:MvpView {
    fun showFraph(list: Map<DateTime, List<Spending>>)
}