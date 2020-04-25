package com.example.holmi_production.money_counter_app.ui.presenters

import com.arellomobile.mvp.MvpView
import com.example.holmi_production.money_counter_app.model.PieCharState

interface ChartPieView :MvpView{
    fun render(state: PieCharState)
}