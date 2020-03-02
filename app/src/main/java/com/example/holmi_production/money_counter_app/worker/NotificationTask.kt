package com.example.holmi_production.money_counter_app.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.holmi_production.money_counter_app.interactor.NotificationInteractor

class NotificationTask constructor(
    private val context: Context,
    private val params: WorkerParameters
) : Worker(context, params) {

    companion object {
        const val ACTION = "NOTIFICATION_INTENT"
    }

    lateinit var notificationInteractor: NotificationInteractor

    override fun doWork(): Result {
        Log.d("M_WorkerTask", "notification tasks start work")
        notificationInteractor.alarmTriggered()
        Log.d("M_WorkerTask", "notification tasks end work")
        return Result.success()
    }
}