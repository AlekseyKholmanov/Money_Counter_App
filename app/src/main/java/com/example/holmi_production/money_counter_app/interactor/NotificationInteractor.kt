package com.example.holmi_production.money_counter_app.interactor

import android.util.Log
import com.example.holmi_production.money_counter_app.notification.NotificationManager
import com.example.holmi_production.money_counter_app.storage.impl.SumPerDayDatabaseImpl
import io.reactivex.disposables.Disposable


class NotificationInteractor(
    private val sumPerDayDatabase: SumPerDayDatabaseImpl,
    private val notificationManager: NotificationManager
) {
    //TODO observer на изменение дня
    suspend fun alarmTriggered() {
        val saved = sumPerDayDatabase.getToday()
        val newToday = sumPerDayDatabase.getAverage()
        sumPerDayDatabase.insertToday(newToday.inc(saved.sum).sum)
        notificationManager.notify(saved.sum, newToday.sum + saved.sum)
    }
}