package com.example.holmi_production.money_counter_app.main

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.holmi_production.money_counter_app.notification.NotificationAlarmReciever

class SaveBalanceTask(context: Context, params: WorkerParameters) : Worker(context, params) {

    companion object {
        const val ACTION = "SAVE_TODAY_BALANCE"
    }

    override fun doWork(): Result {
        Log.d("M_WorkerTask", "balance task start work")
        val intent = Intent(applicationContext, NotificationAlarmReciever::class.java)
        intent.action = ACTION
        applicationContext.sendBroadcast(intent)
        WorkerManager.startBalanceWorker()
        Log.d("M_WorkerTask", "balance task end work")
        return Result.success()
    }
}