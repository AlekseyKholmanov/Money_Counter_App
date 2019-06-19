package com.example.holmi_production.money_counter_app.broadcasts

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.holmi_production.money_counter_app.MainActivity
import com.example.holmi_production.money_counter_app.NotificationManager
import com.example.holmi_production.money_counter_app.R

class DayChangeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("qwerty", "beach fuck")
        send(context)

    }
    private fun send(context: Context){
        val intent = Intent("custom-intent-filter")
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
    }
}
