package com.example.holmi_production.money_counter_app.broadcasts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager

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
