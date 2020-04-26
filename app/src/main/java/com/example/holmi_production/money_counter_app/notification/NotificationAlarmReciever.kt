package com.example.holmi_production.money_counter_app.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.holmi_production.money_counter_app.interactor.BalanceInteractor
import com.example.holmi_production.money_counter_app.interactor.NotificationInteractor
import com.example.holmi_production.money_counter_app.interactor.SpendingInteractor
import com.example.holmi_production.money_counter_app.worker.NotificationTask
import javax.inject.Inject

class NotificationAlarmReciever : BroadcastReceiver() {
    @Inject
    lateinit var notificationInteractor: NotificationInteractor
    @Inject
    lateinit var balanceInteractor: BalanceInteractor
    @Inject
    lateinit var spendingInteractor:SpendingInteractor

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("M_NotAlarmRecivr", "recieve notification intent")
        if (intent.action == NotificationTask.ACTION) {
            notificationInteractor.alarmTriggered()
        }

    }
}
