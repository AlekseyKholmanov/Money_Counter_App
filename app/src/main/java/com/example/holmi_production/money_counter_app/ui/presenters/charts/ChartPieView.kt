package com.example.holmi_production.money_counter_app.ui.presenters.charts

import moxy.MvpView
import com.example.holmi_production.money_counter_app.model.PieCharState

interface ChartPieView :MvpView{
    fun render(state: PieCharState)
}