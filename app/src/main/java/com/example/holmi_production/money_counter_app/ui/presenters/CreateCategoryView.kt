package com.example.holmi_production.money_counter_app.ui.presenters

import moxy.MvpView
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

interface CreateCategoryView:MvpView {

    fun popUp(): Unit
}