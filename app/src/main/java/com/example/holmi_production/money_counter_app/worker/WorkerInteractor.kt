package com.example.holmi_production.money_counter_app.worker

import android.util.Log
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.holmi_production.money_counter_app.utils.Time
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WorkerInteractor @Inject constructor(private val workManager: WorkManager) {
    companion object {

        val NOTIFICATION_WORK_TAG = "NOTIFICATION_TASK"
        val BALANCE_WORK_TAG = "BALANCE_WORK_TAG"
        val BALANCE_POPULATE_TAG = "BALANCE_POPULATE_TAG"
        val END_PERIOD_TAG = "END_PERIOD_TAG"
    }


    fun startNotificationWorker() {
        val diff = Time.getDiffToNextDay(addMinutes = 1)
        val work = PeriodicWorkRequestBuilder<NotificationTask>(24, TimeUnit.HOURS)
            .setInitialDelay(diff, TimeUnit.MILLISECONDS)
            .addTag(NOTIFICATION_WORK_TAG)
            .build()
        workManager.enqueue(work)
    }

    fun startBalanceWorker() {
        val diff = Time.getDiffToNextDay(addMinutes = 5)
        Log.d("M_WorkerManager", "balance work execute")
        val work = PeriodicWorkRequestBuilder<BalanceTask>(24, TimeUnit.HOURS)
            .setInitialDelay(diff, TimeUnit.MILLISECONDS)
            .addTag(BALANCE_WORK_TAG)
            .build()
        workManager.enqueue(work)
    }

    fun startEndMonthWorker(value: Int) {
        val data = Data.Builder().apply {
            putInt("endMonth", value)
        }.build()
        val work = OneTimeWorkRequest.Builder(EndMonthTask::class.java)
            .addTag(END_PERIOD_TAG)
            .setInputData(data)
            .build()
        workManager.enqueue(work)
        Log.d("M_WorkerManager", "notification work executed")
    }

    fun balancePopulateWork() {
        val work = OneTimeWorkRequest.Builder(BalancePopulateTask::class.java)
            .addTag(BALANCE_POPULATE_TAG)
            .build()
        workManager.enqueue(work)
    }

    private fun cancelWorks(tag: String) {
        workManager.cancelAllWorkByTag(tag)
        Log.d("M_WorkerManager", "wrk canceled $tag")
    }

    fun cancelAll() {
        workManager.cancelAllWork()
    }

}