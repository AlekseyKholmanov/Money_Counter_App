package com.example.holmi_production.money_counter_app.ui.charts_fragments.pie

import com.arellomobile.mvp.MvpView
import com.example.holmi_production.money_counter_app.model.entity.Category
import com.example.holmi_production.money_counter_app.model.entity.SpendingWithCategory

interface PieChartView :MvpView{
    fun showPie(data: List<Pair<Category, List<SpendingWithCategory>>>)
    fun showChips(data: List<Pair<Category, List<SpendingWithCategory>>>)
    fun showError()
}