package com.example.holmi_production.money_counter_app.firstLaunch

import com.arellomobile.mvp.InjectViewState
import com.example.holmi_production.money_counter_app.getDayAddition
import com.example.holmi_production.money_counter_app.mvp.BasePresenter
import com.example.holmi_production.money_counter_app.storage.SpendingRepository
import com.example.holmi_production.money_counter_app.toRUformat
import org.joda.time.DateTime
import org.joda.time.Days
import javax.inject.Inject

@InjectViewState
class FirstLaunchPresenter @Inject constructor(private val repository: SpendingRepository) :
    BasePresenter<FirstLaunchView>() {
    private var sum: Int = 0
    private var dif: Int = 0
    fun getSum(sum: Double) {
        this.sum = sum.toInt()
    }

    fun getSumPerDay() {
        viewState.showSumPerDay((sum / dif).toString())
    }

    fun setDate(date: DateTime) {
        dif = Days.daysBetween(DateTime().withTimeAtStartOfDay(), date.withTimeAtStartOfDay()).days
        viewState.showDate(date.toRUformat(), dif.getDayAddition())
    }

}
