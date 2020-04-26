package com.example.holmi_production.money_counter_app.ui.presenters

import android.graphics.Color
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.example.holmi_production.money_counter_app.extensions.*
import com.example.holmi_production.money_counter_app.interactor.CategoryInteractor
import com.example.holmi_production.money_counter_app.interactor.SpendingInteractor
import com.example.holmi_production.money_counter_app.model.SpDirection
import com.example.holmi_production.money_counter_app.model.entity.SpendingEntity
import com.example.holmi_production.money_counter_app.mvp.BasePresenter
import com.example.holmi_production.money_counter_app.storage.SettingRepository
import com.example.holmi_production.money_counter_app.storage.SpendingRepository
import com.example.holmi_production.money_counter_app.storage.SumPerDayRepository
import org.joda.time.DateTime
import org.joda.time.Days
import javax.inject.Inject

@InjectViewState
class KeyboardPresenter @Inject constructor(
    private val sumPerDayRepository: SumPerDayRepository,
    private val settingRepository: SettingRepository,
    private val spendingInteractor: SpendingInteractor,
    private val categoryInteractor: CategoryInteractor,
    private val spendingRepository: SpendingRepository
) :
    BasePresenter<KeyboardFragmnetView>() {

    fun undoAdding(spending: SpendingEntity) {
        spendingInteractor.delete(spending)
            .subscribe({ Log.d("qwerty", "delete") }, { Log.d("qwerty", "error" + it.message) })
            .keep()
    }

    fun saveSpend(sum: Double, comment: String, isSpending: SpDirection, subCategoryId: Int?) {
        val categoryId = settingRepository.getCategoryValue()
        val (startDate, endDate) = settingRepository.getCurrentPeriods()
        val diff = Days.daysBetween(DateTime(), endDate)
        // if currency converter enable
        val sumWithConverter = if (settingRepository.getConverter()) {
            val coef = settingRepository.getConverterValue()
            sum * coef.toDouble()

        } else {
            sum
        }
        val spending = SpendingEntity(
            DateTime(),
            sumWithConverter,
            categoryId,
            subCategoryId,
            isSpending,
            comment
        )
        spendingInteractor.insert(spending)
            .doOnComplete {
                categoryInteractor.getCategoryWithSub(spending.categoryId)
                    .async()
                    .subscribe({
                        viewState.showSnack(it, spending)
                    }, {
                        Log.d("M_KeyboardPresenter", "error ${it.message}")
                    })
                    .keep()
            }
            .complete()
            .keep()

        sumPerDayRepository.getTodayAndAverage()
            .async()
            .subscribe({ it ->
                val today = it.first.sum
                val average = it.second.sum
                //вычитаем сумму из текущего дня
                if (today >= sum && spending.isSpending == SpDirection.SPENDING)
                    sumPerDayRepository.insertToday(today - sum).complete().keep()
                //увеличиваем сумму у всех дней т.к. зарплата
                else if (spending.isSpending == SpDirection.INCOME) {
                    val daysCount = diff.days
                    val deltaAverage = sum / daysCount
                    sumPerDayRepository.insertAverage(average + deltaAverage).complete().keep()
                    sumPerDayRepository.insertToday(today + deltaAverage).complete().keep()
                }
                //сумма сегодня < траты, вычитаем из общей суммы
                else {
                    val daysCount = diff.days - 1
                    val deltaAverage = (sum - today) / daysCount
                    sumPerDayRepository.insertAverage(average - deltaAverage).complete().keep()
                    sumPerDayRepository.insertToday(0.0).complete().keep()
                }
                categoryInteractor
                    .updateUsageCount(categoryId)
                    .keep()
            }, { Log.d("qwerty", it.message) })
            .keep()

    }

    fun observeData() {
        val a = settingRepository.getDaysToEndPeriod()
        Log.d("M_KeyboardPresenter", "ostalos $a")

        spendingRepository.observeSpending()
            .async()
            .subscribe({ list ->
                val spending = list.filter { it.isSpending == SpDirection.SPENDING }.map { it.sum }
                val income = list.filter { it.isSpending == SpDirection.INCOME }.map { it.sum }
                viewState.showIncomeSum((income.sum() - spending.sum()).toCurencyFormat().withRubleSign())
            }, { Log.d("qwerty", it.message) })
            .keep()

        sumPerDayRepository.observeToday()
            .async()
            .subscribe({ today ->
                viewState.showSumPerDay(today.sum.toCurencyFormat().withRubleSign())
            }, { Log.d("qwerty", it.message) })
            .keep()

        sumPerDayRepository.observeAverage()
            .async()
            .subscribe({ average ->
                viewState.showAverageSum(
                    average.sum.toCurencyFormat().withRubleSign(),
                    average.sum >= 0.0
                )
            }, { Log.d("qwerty", it.message) })
            .keep()
    }

    fun observeEndPeriodDate() {
        val current = settingRepository.getDaysToEndPeriod()
        //TODO костыль
        viewState.showDaysLeft(" на $current дней")
        settingRepository.observeEndPeriod()
            .async()
            .subscribe { day ->
                Log.d("M_KeyboardPresenter", "presenter get new end day $day")
                val days = settingRepository.getDaysToEndPeriod()
                recalculateAverageSum(days)
                viewState.showDaysLeft(" на $days дней")
            }
            .keep()
    }

    fun getCategoryButtonValue() {
        val categoryId = settingRepository.getCategoryValue()
        updateKeyboardUI(categoryId)
    }

    fun setCategoryButonType(id: Int) {
        Log.d("M_KeyboardPresenter", "set type $id")
        settingRepository.setCategoryButtonType(id)
        updateKeyboardUI(id)
    }

    private fun recalculateAverageSum(days: Int) {
        Log.d("M_KeyboardPresenter", "start recalculating")
        spendingRepository.getAll()
            .async()
            .subscribe({ list ->
                val spent =
                    list.filter { it.isSpending == SpDirection.SPENDING }.map { it.sum }.sum()
                val income =
                    list.filter { it.isSpending == SpDirection.INCOME }.map { it.sum }.sum()
                val averageSum = (income - spent) / days
                sumPerDayRepository.insertToday(averageSum).complete().keep()
                sumPerDayRepository.insertAverage(averageSum).complete().keep()
                viewState.showNewSumSnack(averageSum, days)
            }, { Log.d("qwerty", it.message) })
            .keep()
    }

    private fun updateKeyboardUI(categoryId: Int) {
        categoryInteractor.getCategoryWithSub(categoryId)
            .map { (category, subCategories) ->
                Pair(category, subCategories.filter { !it.isDeleted })
            }
            .async()
            .subscribe({ pair ->
                viewState.updateCategoryPickerButton(category = pair.first)
                viewState.showSubcategoryMenu(
                    subcategories = pair.second,
                    color = pair.first.color ?: Color.BLACK
                )
                viewState.showActionButtons(directions = pair.first.spendingDirection)
            }, {
                Log.d("M_KeyboardPresenter", it.message)
                viewState.updateCategoryPickerButton(null)
            })
            .keep()
    }

    private fun updateDayLeft() {
        val now = DateTime()
        val endPeriodDate = settingRepository.getEndMonth() - 1
        val toEndMonth = if (DateTime().dayOfMonth > endPeriodDate) {
            now.withTimeAtEndOfMonth(now.monthOfYear).dayOfMonth - now.dayOfMonth + endPeriodDate
        } else {
            endPeriodDate - now.dayOfMonth
        }
        viewState.showDaysLeft(" на ${toEndMonth.getDayAddition()}")
    }
}