package com.example.holmi_production.money_counter_app.ui.presenters

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface CreateCategoryView:MvpView {
    @StateStrategyType(SkipStrategy::class)
    fun dismissDialog()
}