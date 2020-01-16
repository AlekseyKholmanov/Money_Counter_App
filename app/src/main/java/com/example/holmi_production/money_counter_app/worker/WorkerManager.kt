package com.example.holmi_production.money_counter_app.worker

import android.util.Log
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.holmi_production.money_counter_app.utils.Time
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WorkerManager {
    companion object {
        val NOTIFICATION_WORK_TAG = "NOTIFICATION_TASK"
        val BALANCE_WORK_TAG = "BALANCE_WORK_TAG"
        val BALANCE_POPULATE_TAG = "BALANCE_POPULATE_TAG"
        val END_PERIOD_TAG = "END_PERIOD_TAG"


        fun startNotificationWorker(workManager:WorkManager) {
            val diff = Time.getDiffToNextDay(addMinutes = 1)
            Log.d(
                "M_WorkerManager",
                "notification work wiil be ${diff / (1000 * 60 * 60 * 24)} day ${diff / (1000 * 60 * 60)} hours ${diff / 1000 % 60} minutes"
            )
            val work = OneTimeWorkRequest.Builder(NotificationTask::class.java)
                .setInitialDelay(diff, TimeUnit.MILLISECONDS)
                .addTag(NOTIFICATION_WORK_TAG)
                .build()
            workManager.enqueue(work)
            Log.d("M_WorkerManager", "notification work executed")
        }

        fun startBalanceWorker(workManager:WorkManager) {
            val diff = Time.getDiffToNextDay(addMinutes = 2)
            Log.d("M_WorkerManager", "balance work execute")
            Log.d(
                "M_WorkerManager",
                "balance work wiil be ${diff / (1000 * 60 * 60 * 24)} day ${diff / (1000 * 60 * 60)} hours ${diff / 1000 % 60} minutes"
            )
            val work = OneTimeWorkRequest.Builder(BalanceTask::class.java)
                .setInitialDelay(diff, TimeUnit.MILLISECONDS)
                .addTag(BALANCE_WORK_TAG)
                .build()
            workManager.enqueue(work)
            Log.d("M_WorkerManager", "balance work executed")
        }

        fun startEndMonthWorker(value:Int, workManager:WorkManager) {
            val data = Data.Builder().apply {
                putInt("endMonth",value)
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
            WorkManager.getInstance().enqueue(work)
        }

        private fun cancelWorks(tag: String, workManager:WorkManager) {
            workManager.cancelAllWorkByTag(tag)
            Log.d("M_WorkerManager", "wrk canceled $tag")
        }

        fun cancelAll(workManager:WorkManager) {
            cancelWorks(
                BALANCE_WORK_TAG, workManager
            )
            cancelWorks(
                NOTIFICATION_WORK_TAG, workManager
            )

        }
    }
}