package com.example.holmi_production.money_counter_app.ui.keyboard_fragment

import android.content.Context
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.example.holmi_production.money_counter_app.extensions.async
import com.example.holmi_production.money_counter_app.extensions.complete
import com.example.holmi_production.money_counter_app.extensions.getDayAddition
import com.example.holmi_production.money_counter_app.extensions.toCurencyFormat
import com.example.holmi_production.money_counter_app.interactor.SpendingInteractor
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
class KeyboardPresenter @Inject constructor(
    private val spendingRepository: SpendingRepository,
    private val sumPerDayRepository: SumPerDayRepository,
    private val settingRepository: SettingRepository,
    private val spendingInteractor: SpendingInteractor,
    private val contex: Context) :
    BasePresenter<KeyboardFragmnetView>() {

    fun undoAdding(spending: Spending) {
        spendingInteractor.delete(spending)
            .subscribe({Log.d("qwerty", "delete")},{Log.d("qwerty","error" + it.message)})
            .keep()
    }

    fun saveSpend(sum: Double, comment: String, isSpending: Boolean) {
        val categoryType = settingRepository.getCategoryValue()
        val spending = Spending(DateTime(), sum, categoryType, isSpending, comment)
        spendingRepository.insert(spending).complete().keep()

        sumPerDayRepository.getBoth()
            .async()
            .subscribe({ it ->
                val today = it.first.sum
                val average = it.second.sum
                //вычитаем сумму из текущего дня
                if (today >= sum && spending.isSpending)
                    sumPerDayRepository.insertToday(today - sum).complete().keep()
                //увеличиваем сумму у всех дней т.к. зарплата
                else if (!spending.isSpending) {
                    val daysCount = settingRepository.getTillEnd()
                    val deltaAverage = sum / daysCount
                    sumPerDayRepository.insertAverage(average + deltaAverage).complete().keep()
                    sumPerDayRepository.insertToday(today + deltaAverage).complete().keep()
                }
                //сумма сегодня < траты, вычитаем из общей суммы
                else {
                    val daysCount = settingRepository.getTillEnd() - 1
                    val deltaAverage = (sum - today) / daysCount
                    sumPerDayRepository.insertAverage(average - deltaAverage).complete().keep()
                    sumPerDayRepository.insertToday(0.0).complete().keep()
                }
            }, { Log.d("qwerty", it.message) })
            .keep()
        viewState.showAfterAddingSnack(spending)
    }

    fun setObservers() {
        spendingRepository.observeSpending()
            .async()
            .subscribe({ list ->
                val a = list.filter { it.isSpending }.map { it.sum }
                val b = list.filter { !it.isSpending }.map { it.sum }
                viewState.showIncomeSum((b.sum() - a.sum()).toCurencyFormat())
                viewState.showSpentSum(a.sum().toCurencyFormat())
            }, { Log.d("qwerty", it.message) })
            .keep()
        sumPerDayRepository.observeToday()
            .async()
            .subscribe({ today ->
                viewState.showSumPerDay(today.sum.toCurencyFormat())
            }, { Log.d("qwerty", it.message) })
            .keep()
        sumPerDayRepository.observeAverage()
            .async()
            .subscribe({ average ->
                viewState.showAverageSum(average.sum.toCurencyFormat(), average.sum >= 0.0)
            }, { Log.d("qwerty", it.message) })
            .keep()
        settingRepository.observeEndDate()
            .async()
            .subscribe({
                viewState.showDaysLeft(" на ${settingRepository.getTillEnd().getDayAddition()}")
            }, { Log.d("qwerty", it.message) })
            .keep()
    }

    fun getDaysLeft() {
        updateDayLeft()
    }

    fun getCategoryButtonValue() {
        val type = settingRepository.getCategoryValue()
        viewState.updateChooseCategoryButton(type)
    }

    fun setCategoryButonType(type: Int) {
        Log.d("M_KeyboardPresenter", "set type $type")
        settingRepository.setCategoryButtonType(type)
        viewState.updateChooseCategoryButton(type)
    }

    fun recalculateAverageSum(endDate: DateTime) {
        spendingRepository.getAll()
            .async()
            .subscribe({ list ->
                val spent = list.filter { it.isSpending }.map { it.sum }.sum()
                val income = list.filter { !it.isSpending }.map { it.sum }.sum()
                val period = (Days.daysBetween(DateTime.now(), endDate)).days + 1
                val averageSum = (income - spent) / period
                settingRepository.saveEndDate(endDate)
                sumPerDayRepository.insertToday(averageSum).complete().keep()
                sumPerDayRepository.insertAverage(averageSum).complete().keep()
                viewState.showNewSumSnack(averageSum,period)
            }, { Log.d("qwerty", it.message) })
            .keep()
    }

    private fun getCategoryType(type: Int): CategoryType {
        return CategoryType.values()[type]
    }

    private fun updateDayLeft() {
        val diff = settingRepository.getTillEnd()
        viewState.showDaysLeft(" на ${diff.getDayAddition()}")
    }
}