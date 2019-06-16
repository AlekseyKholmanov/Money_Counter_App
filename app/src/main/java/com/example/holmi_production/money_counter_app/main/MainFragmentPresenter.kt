package com.example.holmi_production.money_counter_app.main

import android.content.Context
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.example.holmi_production.money_counter_app.MainActivity
import com.example.holmi_production.money_counter_app.async
import com.example.holmi_production.money_counter_app.getDayAddition
import com.example.holmi_production.money_counter_app.model.*
import com.example.holmi_production.money_counter_app.mvp.BasePresenter
import com.example.holmi_production.money_counter_app.storage.SpendingRepository
import com.example.holmi_production.money_counter_app.storage.SumPerDayRepository
import com.example.holmi_production.money_counter_app.toCurencyFormat
import org.joda.time.DateTime
import org.joda.time.Days
import javax.inject.Inject

@InjectViewState
class MainFragmentPresenter @Inject constructor(
    private val spendRep: SpendingRepository,
    private val perDayRep: SumPerDayRepository,
    private val contex: Context) :
    BasePresenter<MainFragmnetView>() {

    private var type = 0

    fun saveSpend(sum: Double) {
        val time = DateTime().withTimeAtStartOfDay()
        val spending = Spending(null, sum, getCategoryType(type), DateTime())

        spendRep.insert(spending).async().subscribe {}.keep()

        perDayRep.getFromDate(time)
            .async()
            .subscribe { list ->
                val todaySum = list.single { it.dateTime == time }.sum
                if (todaySum >= sum && spending.categoryTypes != Expense.SALARY) {
                    perDayRep.insert(SumPerDay(time, todaySum - sum)).async().subscribe().keep()
                } else {
                    val isSalary = spending.categoryTypes == Expense.SALARY
                    val startIndex = if (isSalary) 0 else 1
                    val startSum = if (isSalary) sum else sum - todaySum
                    val daysCount = (list.count() - startIndex)
                    val averageSum = startSum / daysCount
                    val sumPerDayList = list.toMutableList()
                    for (i in startIndex until list.count()) {
                        sumPerDayList[i].sum =
                            if (isSalary)
                                list[i].sum + averageSum
                            else list[i].sum - averageSum
                    }
                    if (!isSalary)
                        sumPerDayList[0] = SumPerDay(time, 0.0)
                    perDayRep.insert(sumPerDayList).async().subscribe().keep()
                }
            }
            .keep()
    }

    fun getSum() {
        spendRep.observeSpending()
            .async()
            .switchIfEmpty { Log.d("qwerty", "empty") }
            .subscribe { list ->
                val a = list.filter { it.categoryTypes.isSpending }.map { it.sum }.ifEmpty { listOf(0.0) }
                val b = list.filter { !it.categoryTypes.isSpending }.map { it.sum }.ifEmpty { listOf(0.0) }
                viewState.showIncomeSum((b.sum() - a.sum()).toCurencyFormat())
                viewState.showSpentSum(a.sum().toCurencyFormat())
            }
            .keep()
        perDayRep.observeSumPerDay()
            .async()
            .subscribe { list ->
                if (list.count() == 0)
                    return@subscribe
                val today = list.single { it.dateTime == DateTime().withTimeAtStartOfDay() }
                val nextDay = list.single { it.dateTime == DateTime().withTimeAtStartOfDay().plusDays(1) }
                viewState.showSumPerDay(today.sum.toCurencyFormat())
                viewState.showNewSumPerDay(nextDay.sum.toCurencyFormat(), true)
            }
            .keep()
    }

    fun getDaysLeft() {
        val shared = contex.getSharedPreferences(MainActivity.STORAGE_NAME, Context.MODE_PRIVATE)
        val leftDate = shared.getLong(MainActivity.END_PERIOD, 0)
        val diff = Days.daysBetween(DateTime().withTimeAtStartOfDay(), DateTime(leftDate)).days + 1
        viewState.showDaysLeft(" на ${diff.getDayAddition()}")
    }

    fun setType(type: Int) {
        this.type = type
    }

    private fun getCategoryType(type: Int): Expense {
        return Expense.values()[type]
    }
}