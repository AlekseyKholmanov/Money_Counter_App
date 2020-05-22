package com.example.holmi_production.money_counter_app.interactor

import android.util.Log
import com.example.holmi_production.money_counter_app.extensions.complete
import com.example.holmi_production.money_counter_app.notification.NotificationManager
import com.example.holmi_production.money_counter_app.storage.impl.SumPerDayDatabaseImpl
import io.reactivex.disposables.Disposable


class NotificationInteractor(
    private val sumPerDayDatabase: SumPerDayDatabaseImpl,
    private val notificationManager: NotificationManager) {

    //TODO observer на изменение дня
    fun alarmTriggered(): Disposable {
        Log.d("qwert", "alarm triggered")
        return sumPerDayDatabase.getTodayAndAverage()
            .async()
            .subscribe({
                val saved = it.first
                val newToday = it.second
                sumPerDayDatabase.insertToday(newToday.inc(saved.sum).sum).complete()
                notificationManager.notify(saved.sum, newToday.sum + saved.sum)
            }, {
                Log.d("qwerty", it.message)
            })
    }
}