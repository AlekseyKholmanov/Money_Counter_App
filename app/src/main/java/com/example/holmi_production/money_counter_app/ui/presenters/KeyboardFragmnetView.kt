package com.example.holmi_production.money_counter_app.ui.presenters

import moxy.MvpView
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType
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
        category: Pair<CategoryEntity, List<SubCategoryEntity>>,
        spending: SpendingEntity)

    @StateStrategyType(SkipStrategy::class)
    fun updateCategoryPickerButton(category: CategoryEntity?)

    @StateStrategyType(SkipStrategy::class)
    fun showSubcategoryMenu(subcategories: List<SubCategoryEntity>, color: Int)

    @StateStrategyType(SkipStrategy::class)
    fun showActionButtons(directions: List<SpDirection>)
}