package com.example.holmi_production.money_counter_app.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.interactor.NotificationInteractor
import com.example.holmi_production.money_counter_app.storage.SettingRepository
import javax.inject.Inject

class NotificationAlarmReciever : BroadcastReceiver() {

    @Inject
    lateinit var settingRepository: SettingRepository
    @Inject
    lateinit var notificationInteractor: NotificationInteractor

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("M_NotAlarmRecivr", "recieve notification intent")
        if (intent.action == "MY_NOTIFICATION_MESSAGE") {
            App.component.inject(this)
            val endDate = settingRepository.getTillEnd()
            if (endDate <= 0) {
                settingRepository.setIsEnd(true)
                return
            }
            notificationInteractor.alarmTriggered()
        }
    }
}
