package com.example.holmi_production.money_counter_app.keyboard

import com.arellomobile.mvp.MvpView
import com.example.holmi_production.money_counter_app.model.CategoryType
import com.example.holmi_production.money_counter_app.model.Spending

interface KeyboardFragmnetView:MvpView{
    fun showMoney(money:String)
    fun showSpentSum(sum:String)
    fun showIncomeSum(money:String)
    fun showSumPerDay(money:String)
    fun showDaysLeft(days:String)
    fun showAverageSum(sum:String, isDisplayed:Boolean)
    fun showNewSumSnack(sum:Double, days:Int)
    fun showCategoryButton(categoryType: CategoryType)
    fun showAfterAddingSnack(spending: Spending)
}