package com.example.holmi_production.money_counter_app.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.holmi_production.money_counter_app.storage.SettingRepository

class EndMonthTask constructor(
    appContext: Context,
    params: WorkerParameters
    ) : Worker(appContext, params) {

    lateinit var settingRepository:SettingRepository
    override fun doWork(): Result {
        val value = inputData.getInt("endMonth", 1)
//        val pref = context.getSharedPreferences(PreferenceModule.STORAGE_NAME, Context.MODE_PRIVATE)
        settingRepository.setEndMonth(value)
//        pref.edit().putInt(SettingRepository.END_MONTH, value).apply()
        return Result.success()
    }
}

