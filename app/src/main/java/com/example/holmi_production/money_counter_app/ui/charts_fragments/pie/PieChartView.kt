package com.example.holmi_production.money_counter_app.ui.charts_fragments.pie

import com.arellomobile.mvp.MvpView
import com.example.holmi_production.money_counter_app.model.entity.Category
import com.example.holmi_production.money_counter_app.model.entity.GraphEntity
import com.example.holmi_production.money_counter_app.model.entity.Spending
import com.example.holmi_production.money_counter_app.model.entity.SpendingListItem

interface PieChartView :MvpView{
    fun render(state:PieCharState)
}