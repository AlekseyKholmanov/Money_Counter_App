package com.example.holmi_production.money_counter_app.main

import android.util.Log
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.holmi_production.money_counter_app.utils.Time
import java.util.concurrent.TimeUnit

class WorkerManager {
    companion object {
        val NOTIFICATION_WORK_TAG = "NOTIFICATION_TASK"
        val BALANCE_WORK_TAG = "BALANCE_WORK_TAG"
        fun startNotificationWorker() {
            val diff = Time.getDiffToNextDay(addMinutes = 11)
            Log.d("M_WorkerManager", "$diff is diff, notification wor execute")
            val work = OneTimeWorkRequest.Builder(NotificationTask::class.java)
                .setInitialDelay(diff, TimeUnit.MILLISECONDS)
                .addTag(NOTIFICATION_WORK_TAG)
                .build()
            WorkManager.getInstance().enqueue(work)
            Log.d("M_WorkerManager", "notification work executed")
        }

        fun startBalanceWorker() {
            val diff =Time.getDiffToNextDay(addMinutes = 10)
            Log.d("M_WorkerManager","balance work execute")
            val work = OneTimeWorkRequest.Builder(SaveBalanceTask::class.java)
                .setInitialDelay(diff, TimeUnit.MILLISECONDS)
                .addTag(BALANCE_WORK_TAG)
                .build()
            WorkManager.getInstance().enqueue(work)
            Log.d("M_WorkerManager","balance work executed")
        }

        private fun cancelWorks(tag: String) {
            WorkManager.getInstance().cancelAllWorkByTag(tag)
            Log.d("M_WorkerManager", "wrk canceled $tag")
        }

        fun cancelAll(){
            cancelWorks(BALANCE_WORK_TAG)
            cancelWorks(NOTIFICATION_WORK_TAG)

        }
    }
}