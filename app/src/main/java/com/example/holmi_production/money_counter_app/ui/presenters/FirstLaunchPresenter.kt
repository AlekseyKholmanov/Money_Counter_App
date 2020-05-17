package com.example.holmi_production.money_counter_app.ui.presenters

import android.util.Log

import com.example.holmi_production.money_counter_app.extensions.*
import com.example.holmi_production.money_counter_app.model.SpDirection
import com.example.holmi_production.money_counter_app.model.entity.SpendingEntity
import com.example.holmi_production.money_counter_app.storage.SettingRepository
import com.example.holmi_production.money_counter_app.storage.SpendingRepository
import com.example.holmi_production.money_counter_app.storage.SumPerDayRepository
import org.joda.time.DateTime
import org.joda.time.Days



class FirstLaunchPresenter (
    private val spendingRepository: SpendingRepository,
    private val sumPerDayRepository: SumPerDayRepository,
    private val settingRepository: SettingRepository)
 {

    private var sum: Double = 0.0
    private var dif: Int = 0
    private lateinit var endPeriod: DateTime
    private lateinit var today: DateTime
    private var sumPerDay: Double = 0.0

    fun getSum(sum: Double) {
        this.sum = sum
    }

    fun getSumPerDay() {
//        sumPerDay = sum / dif
//        viewState.showSumPerDay(sumPerDay.toCurencyFormat())
    }

    fun updateDate(date: DateTime) {
//        endPeriod = date.withTimeAtStartOfDay()
//        today = DateTime().withTimeAtStartOfDay()
//        dif = (Days.daysBetween(today, endPeriod)).days
//        viewState.showDate(date.toRUformat(), (dif).getDayAddition())
    }

    fun goToMainScreen() {
//        spendingRepository.insert(
//            SpendingEntity(
//                DateTime(),
//                sum,
//                0,
//                null,
//                SpDirection.INCOME,
//                ""
//            )
//        ).async()
//            .subscribe({},{ Log.d("qwerty",it.message)})
//            .keep()
//        sumPerDayRepository.insertAverage(sumPerDay).complete().keep()
//        sumPerDayRepository.insertToday(sumPerDay).complete().keep()
//        settingRepository.setAppOpened()
////        settingRepository.saveStartDate(today)
////        settingRepository.saveEndDate(endPeriod)
//        viewState.showMainScreen()
    }
}
