package com.example.holmi_production.money_counter_app.worker

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.holmi_production.money_counter_app.di.modules.ChildWorkerFactory
import com.example.holmi_production.money_counter_app.interactor.NotificationInteractor
import com.example.holmi_production.money_counter_app.notification.NotificationAlarmReciever
import com.example.holmi_production.money_counter_app.storage.BalanceRepository
import com.example.holmi_production.money_counter_app.storage.SpendingRepository
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import javax.inject.Inject
import javax.inject.Provider

class NotificationTask @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val params: WorkerParameters,
    private val notificationInteractor: NotificationInteractor
) : Worker(context, params) {

    companion object {
        const val ACTION = "NOTIFICATION_INTENT"
    }

    override fun doWork(): Result {
        Log.d("M_WorkerTask", "notification tasks start work")
        notificationInteractor.alarmTriggered()
        WorkerManager.startNotificationWorker(context)
        Log.d("M_WorkerTask", "notification tasks end work")
        return Result.success()
    }

    @AssistedInject.Factory
    interface Factory : ChildWorkerFactory
}