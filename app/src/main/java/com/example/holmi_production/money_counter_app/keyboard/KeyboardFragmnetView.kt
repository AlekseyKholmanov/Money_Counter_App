package com.example.holmi_production.money_counter_app.keyboard

import com.arellomobile.mvp.MvpView
import com.example.holmi_production.money_counter_app.model.CategoryType

interface KeyboardFragmnetView:MvpView{
    fun showMoney(money:String)
    fun showSpentSum(sum:String)
    fun showIncomeSum(money:String)
    fun showSumPerDay(money:String)
    fun showDaysLeft(days:String)
    fun showAverageSum(sum:String, isDisplayed:Boolean)
    fun showSnack(message: String)
    fun showCategoryButton(categoryType: CategoryType)
}