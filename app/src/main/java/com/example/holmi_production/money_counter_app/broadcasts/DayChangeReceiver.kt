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
        val intt = Intent(context, MainActivity::class.java).apply {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
        val pi = PendingIntent.getActivity(context, 0, intt, 0)
        send(context)
        val builder = NotificationCompat.Builder(context, NotificationManager.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("blah blah bla")
            .setContentText("ble ble ble")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pi)
        with(NotificationManagerCompat.from(context)) {
            notify(1, builder.build())
        }
    }
    private fun send(context: Context){
        val intent = Intent("custom-intent-filter")
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
    }
}
