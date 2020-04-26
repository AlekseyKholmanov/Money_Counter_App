package com.example.holmi_production.money_counter_app.ui.presenters

import com.arellomobile.mvp.MvpView
import com.example.holmi_production.money_counter_app.model.entity.SpendingEntity
import org.joda.time.DateTime

interface StackedView:MvpView {
    fun showGraph(list: Map<DateTime, List<SpendingEntity>>)
    fun showError()
}