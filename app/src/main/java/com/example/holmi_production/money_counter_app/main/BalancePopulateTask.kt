package com.example.holmi_production.money_counter_app.main

import android.content.Context
import android.content.Intent
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.holmi_production.money_counter_app.notification.NotificationAlarmReciever

class BalancePopulateTask(context: Context, params: WorkerParameters) : Worker(context, params) {
    companion object{
       const val ACTION = "POPULATION_WORK"
    }
    override fun doWork(): Result {
        val intent = Intent(applicationContext, NotificationAlarmReciever::class.java)
        intent.action = ACTION
        applicationContext.sendBroadcast(intent)
        return Result.success()
    }

}