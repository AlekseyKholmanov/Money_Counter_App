package com.example.holmi_production.money_counter_app.ui.keyboard_fragment

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.holmi_production.money_counter_app.model.SpDirection
import com.example.holmi_production.money_counter_app.model.entity.*

interface KeyboardFragmnetView : MvpView {
    fun showMoney(money: String)
    fun showIncomeSum(money: String)
    fun showSumPerDay(money: String)
    fun showDaysLeft(days: String)
    fun showAverageSum(sum: String, isDisplayed: Boolean)
    fun showNewSumSnack(sum: Double, days: Int)

    fun showSnack(
        category: Pair<Category, List<SubCategory>>,
        spending: Spending)

    @StateStrategyType(SkipStrategy::class)
    fun updateCategoryPickerButton(category: Category?)

    @StateStrategyType(SkipStrategy::class)
    fun showSubcategoryMenu(subcategories: List<SubCategory>, color: Int)

    @StateStrategyType(SkipStrategy::class)
    fun showActionButtons(directions: List<SpDirection>)
}