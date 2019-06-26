package com.example.holmi_production.money_counter_app.main

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.arellomobile.mvp.InjectViewState
import com.example.holmi_production.money_counter_app.*
import com.example.holmi_production.money_counter_app.extensions.async
import com.example.holmi_production.money_counter_app.extensions.complete
import com.example.holmi_production.money_counter_app.extensions.getDayAddition
import com.example.holmi_production.money_counter_app.extensions.toCurencyFormat
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
class MainFragmentPresenter @Inject constructor(
    private val spendingRepository: SpendingRepository,
    private val sumPerDayRepository: SumPerDayRepository,
    private val settingRepository: SettingRepository,
    private val contex: Context,
    private val notificationManager: NotificationManager) :
    BasePresenter<MainFragmnetView>() {

    fun saveSpend(sum: Double) {
        val categoryType = settingRepository.getCategoryValue()
        val spending = Spending(null, sum, getCategoryType(categoryType), DateTime())
        spendingRepository.insert(spending).complete().keep()

        sumPerDayRepository.getBoth()
            .async()
            .subscribe { it ->
                val today = it.first.sum
                val average = it.second.sum
                //вычитаем сумму из текущего дня
                if (today >= sum && spending.categoryTypes != CategoryType.SALARY)
                    sumPerDayRepository.insertToday(today - sum).complete().keep()
                //увеличиваем сумму у всех дней т.к. зарплата
                else if (spending.categoryTypes == CategoryType.SALARY) {
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
            }
            .keep()
    }

    fun initializeNotification() {
        notificationManager.setNotificationTime()
    }

    fun setObservers() {
        Log.d("qwerty", "start Obserfve + ${this.hashCode()}")

        spendingRepository.observeSpending()
            .async()
            .subscribe { list ->
                val a = list.filter { it.categoryTypes.isSpending }.map { it.sum }
                val b = list.filter { !it.categoryTypes.isSpending }.map { it.sum }
                viewState.showIncomeSum((b.sum() - a.sum()).toCurencyFormat())
                viewState.showSpentSum(a.sum().toCurencyFormat())
            }
            .keep()
        sumPerDayRepository.observeToday()
            .async()
            .subscribe { today ->
                viewState.showSumPerDay(today.sum.toCurencyFormat())
            }
            .keep()
        sumPerDayRepository.observeAverage()
            .async()
            .subscribe { average ->
                viewState.showAverageSum(average.sum.toCurencyFormat(), true)
            }
            .keep()
        settingRepository.observeCategoryValue()
            .async()
            .subscribe {
                viewState.showCategoryButton(CategoryType.values()[it])
            }
            .keep()
        settingRepository.observeEndDate()
            .async()
            .subscribe {
                viewState.showDaysLeft(" на ${settingRepository.getTillEnd().getDayAddition()}")
            }
            .keep()
    }

    fun getDaysLeft() {
        updateDayLeft()
    }

    fun getCategoryButtonValue() {
        val type = settingRepository.getCategoryValue()
        viewState.showCategoryButton(getCategoryType(type))
    }

    fun setType(type: Int) {
        settingRepository.setCategoryButtonType(type)
    }

    fun alarmTriggered() {
        Log.d("qwert", "alarm triggered")
        sumPerDayRepository.getBoth()
            .async()
            .subscribe { it ->
                val yesterday = it.first
                val newTodaySum = it.second
                sumPerDayRepository.insertToday(newTodaySum.inc(yesterday.sum).sum).complete().keep()

                val notification = buildNotification(yesterday.sum, newTodaySum.sum)
                sendNotification(notification)
            }
            .keep()
        updateDayLeft()
    }

    fun recalculateAverageSum(endDate: DateTime) {
        spendingRepository.getAll()
            .async()
            .subscribe { list ->
                val spent = list.filter { it.categoryTypes.isSpending }.map { it.sum }.sum()
                val income = list.filter { !it.categoryTypes.isSpending }.map { it.sum }.sum()
                val period = (Days.daysBetween(DateTime.now(), endDate)).days + 1
                val averageSum = (income - spent) / period
                settingRepository.saveEndDate(endDate)
                sumPerDayRepository.insertToday(averageSum).complete().keep()
                sumPerDayRepository.insertAverage(averageSum).complete().keep()
                viewState.showSnack("новая сумма: $averageSum на ${period.getDayAddition()}")
            }
            .keep()
    }

    private fun getCategoryType(type: Int): CategoryType {
        return CategoryType.values()[type]
    }

    private fun updateDayLeft() {
        val diff = settingRepository.getTillEnd()
        viewState.showDaysLeft(" на ${diff.getDayAddition()}")
    }

    private fun buildNotification(saveSum: Double, newSum: Double): Notification {
        val intent = Intent(contex, MainActivity::class.java).apply {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
        val pIntent = PendingIntent.getActivity(contex, 0, intent, 0)
        return NotificationCompat.Builder(contex, NotificationManager.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Итоги дня")
            .setContentText("ble ble ble")
            .setStyle(
                NotificationCompat.InboxStyle()
                    .addLine("Вчера сэкономлено:${saveSum.toCurencyFormat()}")
                    .addLine("Сумма на сегодня :${newSum.toCurencyFormat()}")
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pIntent)
            .build()
    }

    private fun sendNotification(notification: Notification) {
        with(NotificationManagerCompat.from(contex)) {
            notify(1, notification)
        }
    }
}