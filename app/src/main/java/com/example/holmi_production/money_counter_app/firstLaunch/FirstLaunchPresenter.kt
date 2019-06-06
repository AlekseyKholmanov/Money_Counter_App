package com.example.holmi_production.money_counter_app.firstLaunch

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.example.holmi_production.money_counter_app.*
import com.example.holmi_production.money_counter_app.model.Expense
import com.example.holmi_production.money_counter_app.model.CategoryType
import com.example.holmi_production.money_counter_app.model.Spending
import com.example.holmi_production.money_counter_app.model.SumPerDay
import com.example.holmi_production.money_counter_app.mvp.BasePresenter
import com.example.holmi_production.money_counter_app.storage.SpendingRepository
import com.example.holmi_production.money_counter_app.storage.SumPerDayRepository
import org.joda.time.DateTime
import org.joda.time.Days
import javax.inject.Inject

@InjectViewState
class FirstLaunchPresenter @Inject constructor(private val spendingRep: SpendingRepository, private val perDayRep:SumPerDayRepository, val context:Context) :
    BasePresenter<FirstLaunchView>() {
    private var sum: Double = 0.0
    private var dif: Int = 0
    private lateinit var endPeriod:DateTime
    private lateinit var startPeriod: DateTime
    private var sumPerDay:Double = 0.0
    fun getSum(sum: Double) {
        this.sum = sum
    }

    fun getSumPerDay() {
        sumPerDay = sum.toDouble()/dif
        viewState.showSumPerDay(sumPerDay.toString().toCurencyFormat())
    }

    fun updateDate(date: DateTime) {
        endPeriod = date.withTimeAtStartOfDay()
        startPeriod = DateTime().withTimeAtStartOfDay()
        dif = Days.daysBetween(startPeriod, endPeriod).days
        viewState.showDate(date.toRUformat(), dif.getDayAddition())
    }

    fun goToMainScreen() {
        spendingRep.insert(
            Spending(
                null,
                sum,
                CategoryType(Expense.SALARY, false),
                DateTime.now().withTimeAtStartOfDay()
            )
        ).async()
            .subscribe()
            .keep()

        val list = arrayListOf<SumPerDay>()
        for(i in 0..dif){
            list.add(SumPerDay(startPeriod.plusDays(i),sumPerDay))
        }
        perDayRep.insert(list)
            .async()
            .subscribe()
            .keep()

        val preferences = context.getSharedPreferences(MainActivity.STORAGE_NAME,Context.MODE_PRIVATE)
        preferences.edit()
            .putLong(MainActivity.START_PERIOD, startPeriod.millis)
            .putLong(MainActivity.END_PERIOD, endPeriod.millis)
            .apply()
        viewState.showMainScreen()
    }

}
