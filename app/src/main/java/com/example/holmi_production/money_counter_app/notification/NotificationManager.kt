package com.example.holmi_production.money_counter_app.notification

import android.app.*
import android.app.NotificationManager
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.extensions.toCurencyFormat
import com.example.holmi_production.money_counter_app.main.MainActivity
import javax.inject.Inject
import javax.inject.Singleton


class NotificationManager @Inject constructor(
    private val notificationManager: NotificationManager,
    private val context: Context) {

    init {
        createNotificationChannel()
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



    private fun buildSpendingNotification(saveSum: Double, newSum: Double): Notification {
        val intent = Intent(context.applicationContext, MainActivity::class.java).apply {
            FLAG_UPDATE_CURRENT
        }
        val pIntent = PendingIntent.getActivity(context.applicationContext, 0, intent, 0)
        return NotificationCompat.Builder(context.applicationContext, CHANNEL_ID)
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

    private fun buillWorkerNotification(balance:Double): Notification {
        return NotificationCompat.Builder(context.applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("balance worker Task")
            .setContentText("выполнено жи")
            .setStyle(
                NotificationCompat.InboxStyle()
                    .addLine("баланс: $balance")
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()
    }

    fun notify(savedSum: Double, newSumPerDay: Double) {
        val notification = buildSpendingNotification(savedSum, newSumPerDay)
        sendNotification(notification, 1)
    }

    fun notifyWorkerTask(balance:Double){
        val notification = buillWorkerNotification(balance)
        sendNotification(notification, 2)
    }

    private fun sendNotification(notification: Notification, id:Int) {
        with(NotificationManagerCompat.from(context.applicationContext)) {
            notify(id, notification)
        }
    }

    companion object {
        const val CHANNEL_ID = "notifications"
        private const val CHANNEL_NAME = "Уведомления"
    }
}