package com.example.holmi_production.money_counter_app.worker

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.holmi_production.money_counter_app.notification.NotificationAlarmReciever

class NotificationTask(context: Context, params: WorkerParameters) : Worker(context, params) {

    companion object{
        const val ACTION = "NOTIFICATION_INTENT"
    }
    override fun doWork(): Result {
        Log.d("M_WorkerTask","notification tasks start work")
        val intent = Intent(applicationContext, NotificationAlarmReciever::class.java)
        intent.action =
            ACTION
        applicationContext.sendBroadcast(intent)
        WorkerManager.startNotificationWorker()
        Log.d("M_WorkerTask","notification tasks end work")
        return Result.success()
    }
}