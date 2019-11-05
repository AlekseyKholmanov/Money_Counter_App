package com.example.holmi_production.money_counter_app.ui.keyboard_fragment

import com.arellomobile.mvp.MvpView
import com.example.holmi_production.money_counter_app.model.SpDirection
import com.example.holmi_production.money_counter_app.model.entity.Category
import com.example.holmi_production.money_counter_app.model.entity.Spending
import com.example.holmi_production.money_counter_app.model.entity.SpendingWithCategory
import com.example.holmi_production.money_counter_app.model.entity.SubCategory

interface KeyboardFragmnetView:MvpView{
    fun showMoney(money:String)
    fun showIncomeSum(money:String)
    fun showSumPerDay(money:String)
    fun showDaysLeft(days:String)
    fun showAverageSum(sum:String, isDisplayed:Boolean)
    fun showNewSumSnack(sum:Double, days:Int)
    fun showSnack(spending: SpendingWithCategory)
    fun updateCategoryPickerButton(category: Category?)
    fun showSubcategoryMenu(subcategories:List<SubCategory>, color:Int)
    fun showActionButtons(directions:List<SpDirection>)
}