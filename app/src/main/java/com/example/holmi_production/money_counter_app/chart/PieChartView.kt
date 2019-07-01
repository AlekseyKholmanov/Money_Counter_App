package com.example.holmi_production.money_counter_app.chart

import com.arellomobile.mvp.MvpView
import com.example.holmi_production.money_counter_app.model.CategoryType
import com.example.holmi_production.money_counter_app.model.Spending

interface PieChartView :MvpView{
    fun showPie(data: Map<CategoryType, List<Spending>>)
}