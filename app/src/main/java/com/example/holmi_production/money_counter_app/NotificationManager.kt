package com.example.holmi_production.money_counter_app

import android.app.*
import android.app.NotificationManager
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import com.example.holmi_production.money_counter_app.broadcasts.DayChangeReceiver
import org.joda.time.DateTime
import javax.inject.Inject

class NotificationManager @Inject constructor(
    private val notificationManager: NotificationManager,
    private val alarmManager: AlarmManager,
    private val application: Application) {
    init {
        createNotificationChannel()
    }

    fun setNotificationTime() {
        val time = DateTime().withTimeAtStartOfDay().plusDays(1).plusHours(1).plusMinutes(0)
        val intent = Intent(application, DayChangeReceiver::class.java)
        val pi = PendingIntent.getBroadcast(application, 0, intent, 0)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, time.millis, AlarmManager.INTERVAL_DAY, pi)
        Log.d("qwerty", "Notification time setted")
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
            channel.description = "My channel description"
            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.enableVibration(false)
            notificationManager.createNotificationChannel(channel)

            Log.d("qwerty", "initialize channel")
        }
    }

    companion object {
        const val CHANNEL_ID = "notifications"
        private const val CHANNEL_NAME = "Уведомления"
    }
}