package com.example.holmi_production.money_counter_app.ui.charts_fragments.pie

import com.arellomobile.mvp.MvpView
import com.example.holmi_production.money_counter_app.model.CategoryType
import com.example.holmi_production.money_counter_app.model.entity.Spending

interface PieChartView :MvpView{
    fun showPie(data: Map<CategoryType, List<Spending>>)
    fun showChips(data: Map<CategoryType, List<Spending>>)
    fun showError()
}