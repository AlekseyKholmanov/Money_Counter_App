package com.example.holmi_production.money_counter_app.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.holmi_production.money_counter_app.storage.SettingRepository

class EndMonthTask(
    private val appContext: Context,
    private val params: WorkerParameters
) : Worker(appContext, params) {
    lateinit var settingRepository: SettingRepository

    override fun doWork(): Result {
        Log.d("M_EndMonthTask", "Start")
        val value = inputData.getInt("endMonth", 1)
//        val pref = context.getSharedPreferences(PreferenceModule.STORAGE_NAME, Context.MODE_PRIVATE)
        settingRepository.setEndMonth(value)
        Log.d("M_EndMonthTask", "complete $value")
//        pref.edit().putInt(SettingRepository.END_MONTH, value).apply()
        return Result.success()
    }
}

