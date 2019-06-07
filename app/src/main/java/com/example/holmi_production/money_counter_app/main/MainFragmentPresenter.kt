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
import io.reactivex.rxkotlin.Flowables
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
    private var sumPerDay = 0.0

    fun saveSpend(sum:Double) {
        val spending = Spending(null, sum, getCategoryType(type), DateTime())
        spendRep.insert(spending)
            .async()
            .subscribe {}
            .keep()
        sumPerDay = if(sum>sumPerDay) 0.0 else sumPerDay-sum
        perDayRep.insert(SumPerDay(DateTime().withTimeAtStartOfDay(),sumPerDay))
            .async()
            .subscribe()
            .keep()
        viewState.showSumPerDay(sumPerDay.toCurencyFormat())
    }

    fun getSum() {
        Flowables.zip(
            spendRep.getSpentSum(),
            spendRep.getIncomeSum()
        )
            .async()
            .subscribe({ (spent, income) ->
                viewState.showSpentSum(spent.sum().toCurencyFormat())
                viewState.showIncomeSum((income.sum() - spent.sum()).toCurencyFormat())

            }, { t -> Log.d("qwerty", t.toString()) })
            .keep()
        perDayRep.getByDate(DateTime().withTimeAtStartOfDay())
            .async()
            .subscribe {
                sumPerDay = it.sum
                viewState.showSumPerDay(it.sum.toCurencyFormat())
            }
            .keep()
    }

    fun getDaysLeft() {
        val shared = contex.getSharedPreferences(MainActivity.STORAGE_NAME, Context.MODE_PRIVATE)
        val leftDate = shared.getLong(MainActivity.END_PERIOD, 0)
        val diff = Days.daysBetween(DateTime().withTimeAtStartOfDay(), DateTime(leftDate)).days
        viewState.showDaysLeft(" на ${diff.getDayAddition()}")
    }


    fun setType(type: Int) {
        this.type = type
    }

    private fun getCategoryType(type: Int): CategoryType {
        val enum = Expense.values()[type]
        return CategoryType.list.single { it.expense == enum }
    }
}