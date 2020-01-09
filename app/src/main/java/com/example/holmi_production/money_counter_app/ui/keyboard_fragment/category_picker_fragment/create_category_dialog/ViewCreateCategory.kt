package com.example.holmi_production.money_counter_app.ui.keyboard_fragment.category_picker_fragment.create_category_dialog

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface ViewCreateCategory:MvpView {
    @StateStrategyType(SkipStrategy::class)
    fun dismissDialog()
}