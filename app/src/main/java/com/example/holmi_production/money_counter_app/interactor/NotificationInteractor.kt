package com.example.holmi_production.money_counter_app.interactor

import android.content.Context
import android.util.Log
import com.example.holmi_production.money_counter_app.extensions.async
import com.example.holmi_production.money_counter_app.extensions.complete
import com.example.holmi_production.money_counter_app.notification.NotificationManager
import com.example.holmi_production.money_counter_app.storage.SumPerDayRepository
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class NotificationInteractor @Inject constructor(
    private val sumPerDayRepository: SumPerDayRepository,
    private val notificationManager: NotificationManager) {

    //TODO observer на изменение дня
    fun alarmTriggered(): Disposable {
        Log.d("qwert", "alarm triggered")
        return sumPerDayRepository.getBoth()
            .async()
            .subscribe { it ->
                val saved = it.first
                val newToday = it.second
                sumPerDayRepository.insertToday(newToday.inc(saved.sum).sum).complete()
                notificationManager.notify(saved.sum, newToday.sum)
            }
    }
}