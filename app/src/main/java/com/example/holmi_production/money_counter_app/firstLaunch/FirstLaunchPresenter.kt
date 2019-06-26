package com.example.holmi_production.money_counter_app.firstLaunch

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.example.holmi_production.money_counter_app.extensions.async
import com.example.holmi_production.money_counter_app.extensions.complete
import com.example.holmi_production.money_counter_app.extensions.getDayAddition
import com.example.holmi_production.money_counter_app.extensions.toRUformat
import com.example.holmi_production.money_counter_app.model.CategoryType
import com.example.holmi_production.money_counter_app.model.Spending
import com.example.holmi_production.money_counter_app.mvp.BasePresenter
import com.example.holmi_production.money_counter_app.storage.SettingRepository
import com.example.holmi_production.money_counter_app.storage.SpendingRepository
import com.example.holmi_production.money_counter_app.storage.SumPerDayRepository
import org.joda.time.DateTime
import org.joda.time.Days
import javax.inject.Inject

@InjectViewState
class FirstLaunchPresenter @Inject constructor(
    private val spendingRepository: SpendingRepository,
    private val sumPerDayRepository: SumPerDayRepository,
    private val settingRepository:SettingRepository,
    val context: Context) :
    BasePresenter<FirstLaunchView>() {
    private var sum: Double = 0.0
    private var dif: Int = 0
    private lateinit var endPeriod: DateTime
    private lateinit var today: DateTime
    private var sumPerDay: Double = 0.0
    fun getSum(sum: Double) {
        this.sum = sum
    }

    fun getSumPerDay() {
        sumPerDay = sum / dif
        sumPerDay = String.format("%.1f", sumPerDay).toDouble()
        viewState.showSumPerDay(sumPerDay.toString())
    }

    fun updateDate(date: DateTime) {
        endPeriod = date.withTimeAtStartOfDay()
        today = DateTime().withTimeAtStartOfDay()
        dif = (Days.daysBetween(today, endPeriod) + 1).days
        viewState.showDate(date.toRUformat(), (dif).getDayAddition())
    }

    fun goToMainScreen() {
        spendingRepository.insert(
            Spending(null,sum,CategoryType.SALARY,today)
        ).async().subscribe().keep()
        sumPerDayRepository.insertAverage(sumPerDay).complete().keep()
        sumPerDayRepository.insertToday(sumPerDay).complete().keep()
        settingRepository.setAppOpened()
        settingRepository.saveStartDate(today)
        settingRepository.saveEndDate(endPeriod)
        viewState.showMainScreen()
    }

}
