package com.example.holmi_production.money_counter_app.notification

import android.app.*
import android.app.NotificationManager
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.extensions.toCurencyFormat
import com.example.holmi_production.money_counter_app.main.MainActivity
import com.example.holmi_production.money_counter_app.storage.SettingRepository
import org.joda.time.DateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationManager @Inject constructor(
    private val notificationManager: NotificationManager,
    private val alarmManager: AlarmManager,
    private val application: Application,
    private val settingRepository: SettingRepository) {
    init {
        createNotificationChannel()
    }

    fun setNotificationTime() {
        val time = DateTime().withTimeAtStartOfDay()
        val intent = Intent(application, NotificationAlarmReciever::class.java)
        val pi = PendingIntent.getBroadcast(application, 0, intent, FLAG_UPDATE_CURRENT)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, time.millis, AlarmManager.INTERVAL_DAY, pi)
        Log.d("qwerty", "Notification time setted")
        settingRepository.setNotificationTime()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
            channel.description = "My channel description"
            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.enableVibration(false)
            notificationManager.createNotificationChannel(channel)
            Log.d("qwerty", "initialize channel")
        }
    }

    private fun buildNotification(saveSum: Double, newSum: Double): Notification {
        val intent = Intent(application, MainActivity::class.java).apply {
            FLAG_UPDATE_CURRENT
        }
        val pIntent = PendingIntent.getActivity(application, 0, intent, 0)
        return NotificationCompat.Builder(application, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Итоги дня")
            .setContentText("ble ble ble")
            .setStyle(
                NotificationCompat.InboxStyle()
                    .addLine("Вчера сэкономлено:${saveSum.toCurencyFormat()}")
                    .addLine("Сумма на сегодня :${newSum.toCurencyFormat()}")
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pIntent)
            .setAutoCancel(true)
            .build()
    }

    fun notify(savedSum: Double, newSumPerDay: Double) {
        val notification = buildNotification(savedSum, newSumPerDay)
        sendNotification(notification)
    }

    private fun sendNotification(notification: Notification) {
        with(NotificationManagerCompat.from(application)) {
            notify(1, notification)
        }
    }

    companion object {
        const val CHANNEL_ID = "notifications"
        private const val CHANNEL_NAME = "Уведомления"
    }
}