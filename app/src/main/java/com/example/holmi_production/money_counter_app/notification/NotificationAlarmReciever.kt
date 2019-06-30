package com.example.holmi_production.money_counter_app.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.storage.SettingRepository
import javax.inject.Inject

class NotificationAlarmReciever: BroadcastReceiver() {

    @Inject lateinit var settingRepository: SettingRepository

    override fun onReceive(context: Context, intent: Intent) {

        Log.d("qwerty", "notification received")
        App.component.inject(this)
        val endDate = settingRepository.getTillEnd()
        if (endDate <= 0)
            settingRepository.setIsEnd(true)
        send(context)
    }

    private fun send(context: Context) {
        val intent = Intent("custom-intent-filter")
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
    }
}
