package com.example.holmi_production.money_counter_app.main

import android.content.Context
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.example.holmi_production.money_counter_app.MainActivity
import com.example.holmi_production.money_counter_app.async
import com.example.holmi_production.money_counter_app.getDayAddition
import com.example.holmi_production.money_counter_app.model.ButtonTypes
import com.example.holmi_production.money_counter_app.model.Expense
import com.example.holmi_production.money_counter_app.model.CategoryType
import com.example.holmi_production.money_counter_app.model.Spending
import com.example.holmi_production.money_counter_app.mvp.BasePresenter
import com.example.holmi_production.money_counter_app.storage.SpendingRepository
import com.example.holmi_production.money_counter_app.storage.SumPerDayRepository
import com.example.holmi_production.money_counter_app.toCurencyFormat
import io.reactivex.rxkotlin.Flowables
import org.joda.time.DateTime
import org.joda.time.Days
import javax.inject.Inject

@InjectViewState
class MainFragmentPresenter @Inject constructor(
    private val spendRep: SpendingRepository,
    private val sumPerDay: SumPerDayRepository,
    private val contex:Context) :
    BasePresenter<MainFragmnetView>() {

    var sum = ""
    private var type = 0

    private fun saveSpending() {
        if (sum == "")
            return
        val spending = Spending(null, sum.toFloat(), getCategoryType(type), DateTime())
        spendRep.insert(spending)
            .async()
            .subscribe {
                Log.d("qwerty", "inserted")
            }
            .keep()
    }

    fun getSum() {
        Flowables.zip(
            spendRep.getSpentSum(),
            spendRep.getIncomeSum(),
            sumPerDay.getByDate(DateTime().withTimeAtStartOfDay())
        )
            .async()
            .subscribe { (spent, income, sumPerDay) ->
                val spentSum = spent.sum()
                viewState.showSpentSum(spentSum.toString())
                viewState.showIncomeSum((income.sum() - spentSum).toString())
                viewState.showSumPerDay(sumPerDay.sum.toString().toCurencyFormat())
            }
            .keep()
    }

    fun getDaysLeft(){
        val shared = contex.getSharedPreferences(MainActivity.STORAGE_NAME,Context.MODE_PRIVATE)
        val leftDate = shared.getLong(MainActivity.END_PERIOD,0)
        val diff = Days.daysBetween(DateTime().withTimeAtStartOfDay(), DateTime(leftDate)).days
        viewState.showDaysLeft(" на ${diff.getDayAddition()}")
    }

    fun buttonPressed(buttonTypes: ButtonTypes, value: String? = null) {
        when (buttonTypes) {
            ButtonTypes.DELETE -> {
                sum = sum.dropLast(1)
                if (sum.takeLast(1) == ".")
                    sum = sum.dropLast(1)
            }
            ButtonTypes.DIVIDER -> {
                when {
                    value == "." && sum.contains(".") -> return
                    sum == "" -> sum = "0."
                    else -> sum += value
                }
            }
            ButtonTypes.ZERO -> {
                when (sum) {
                    "" -> return
                    else -> sum += value
                }
            }
            ButtonTypes.ENTER -> {
                saveSpending()
                sum = ""
            }
            ButtonTypes.NUMERIC -> {
                if (sum.contains('.') && sum.takeLast(1) != ".")
                    sum = sum.dropLast(1)
                sum += value
            }
        }
        viewState.showMoney(sum.toCurencyFormat())
    }

    fun setType(type: Int) {
        this.type = type

    }

    private fun getCategoryType(type: Int): CategoryType {
        val enum = Expense.values()[type]
        return CategoryType.list.single { it.expense == enum }
    }
}