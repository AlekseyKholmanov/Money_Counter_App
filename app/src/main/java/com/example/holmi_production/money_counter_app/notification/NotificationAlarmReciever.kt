package com.example.holmi_production.money_counter_app.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.holmi_production.money_counter_app.App

class NotificationAlarmReciever : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        App.component.inject(this)
        Log.d("qwerty", "notification received")
        send(context)
    }

    private fun send(context: Context) {
        val intent = Intent("custom-intent-filter")
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
    }
}
