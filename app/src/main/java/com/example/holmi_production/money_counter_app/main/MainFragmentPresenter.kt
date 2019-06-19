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
import com.example.holmi_production.money_counter_app.model.*
import com.example.holmi_production.money_counter_app.mvp.BasePresenter
import com.example.holmi_production.money_counter_app.storage.SettingRepository
import com.example.holmi_production.money_counter_app.storage.SpendingRepository
import com.example.holmi_production.money_counter_app.storage.SumPerDayRepository
import org.joda.time.DateTime
import org.joda.time.Days
import javax.inject.Inject

@InjectViewState
class MainFragmentPresenter @Inject constructor(
    private val spendRep: SpendingRepository,
    private val perDayRep: SumPerDayRepository,
    private val settings: SettingRepository,
    private val contex: Context,
    private val notificationManager: NotificationManager) :
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

    fun initializeNotification() {
        notificationManager.setNotificationTime()
    }

    fun getSum() {
        spendRep.observeSpending()
            .async()
            .doOnError {
                Log.d("qwerty", "error")
            }
            .subscribe { list ->
                val a = list.filter { it.categoryTypes.isSpending }.map { it.sum }
                val b = list.filter { !it.categoryTypes.isSpending }.map { it.sum }
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
        updateDayLeft()
        settings.observeEndDate()
            .subscribe({
                viewState.showDaysLeft(" на ${getDiffDay(it).getDayAddition()}")
            }, { Log.d("qwerty", it.toString()) })
            .keep()
    }

    fun setType(type: Int) {
        this.type = type
    }

    fun alarmTriggered() {
        Log.d("qwert", "alarm triggered")
        perDayRep.getFromDate(DateTime().withTimeAtStartOfDay().minusDays(1))
            .async()
            .subscribe { it ->
                val yesterday = it[0]
                val newTodaySum = it[1]
                newTodaySum.sum+=yesterday.sum
                if (yesterday.sum == 0.0)
                    return@subscribe
                else {
                    perDayRep.insert(newTodaySum).async().subscribe().keep()
                }
                val notification = buildNotification(yesterday.sum, newTodaySum.sum)
                sendNotification(notification)
            }
            .keep()
        updateDayLeft()
    }

    private fun getCategoryType(type: Int): Expense {
        return Expense.values()[type]
    }

    private fun getDiffDay(date: Long): Int {
        return Days.daysBetween(DateTime().withTimeAtStartOfDay(), DateTime(date)).days + 1
    }

    private fun updateDayLeft() {
        val date = settings.getEndPeriod()
        val diff = getDiffDay(date)
        viewState.showDaysLeft(" на ${diff.getDayAddition()}")
    }

    private fun buildNotification(saveSum: Double, newSum: Double): Notification {
        val intt = Intent(contex, MainActivity::class.java).apply {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
        val intent = PendingIntent.getActivity(contex, 0, intt, 0)
        return NotificationCompat.Builder(contex, NotificationManager.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Итоги дня")
            .setContentText("ble ble ble")
            .setStyle(
                NotificationCompat.InboxStyle()
                    .addLine("Удалось сэкономить вчера:${saveSum.toCurencyFormat()}")
                    .addLine("Сумма на сегодня :${newSum.toCurencyFormat()}")
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(intent)
            .build()
    }

    private fun sendNotification(notification: Notification) {
        with(NotificationManagerCompat.from(contex)) {
            notify(1, notification)
        }
    }
}