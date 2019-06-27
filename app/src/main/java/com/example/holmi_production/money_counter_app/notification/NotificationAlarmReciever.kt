package com.example.holmi_production.money_counter_app.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.interactor.NotificationInteractor
import javax.inject.Inject

class NotificationAlarmReciever @Inject constructor(): BroadcastReceiver() {

    @Inject lateinit var notificationInteractor: NotificationInteractor

    override fun onReceive(context: Context, intent: Intent) {
        App.component.inject(this)
        Log.d("qwerty", "notification received")
        notificationInteractor.alarmTriggered()
    }
}
