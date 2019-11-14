package com.example.holmi_production.money_counter_app.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.interactor.BalanceInteractor
import com.example.holmi_production.money_counter_app.interactor.NotificationInteractor
import com.example.holmi_production.money_counter_app.main.NotificationTask
import com.example.holmi_production.money_counter_app.main.SaveBalanceTask
import com.example.holmi_production.money_counter_app.storage.SettingRepository
import javax.inject.Inject

class NotificationAlarmReciever : BroadcastReceiver() {

    init {
        App.component.inject(this)
    }

    @Inject
    lateinit var settingRepository: SettingRepository
    @Inject
    lateinit var notificationInteractor: NotificationInteractor
    @Inject
    lateinit var balanceInteractor: BalanceInteractor

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("M_NotAlarmRecivr", "recieve notification intent")
        if (intent.action == NotificationTask.ACTION) {
            val endDate = settingRepository.getTillEnd()
            if (endDate <= 0) {
                settingRepository.setIsEnd(true)
                return
            }
            notificationInteractor.alarmTriggered()
        }
        if(intent.action == SaveBalanceTask.ACTION){
            balanceInteractor.insert()
        }
    }
}
