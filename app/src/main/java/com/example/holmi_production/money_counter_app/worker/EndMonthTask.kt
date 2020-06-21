package com.example.holmi_production.money_counter_app.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.holmi_production.money_counter_app.storage.AppPreference

class EndMonthTask(
    private val appContext: Context,
    private val params: WorkerParameters
) : Worker(appContext, params) {
    lateinit var appPreference: AppPreference

    override fun doWork(): Result {
        Log.d("M_EndMonthTask", "Start")
        val value = inputData.getInt("endMonth", 1)
        appPreference.setEndMonth(value)
        Log.d("M_EndMonthTask", "complete $value")
        return Result.success()
    }
}

