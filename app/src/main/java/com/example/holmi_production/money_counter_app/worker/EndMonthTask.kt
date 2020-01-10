package com.example.holmi_production.money_counter_app.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.holmi_production.money_counter_app.di.modules.PreferenceModule
import com.example.holmi_production.money_counter_app.storage.SettingRepository

class EndMonthTask(val context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        val value = inputData.getInt("endMonth",1)
        val pref = context.getSharedPreferences(PreferenceModule.STORAGE_NAME, Context.MODE_PRIVATE)
        pref.edit().putInt(SettingRepository.END_MONTH,value).apply()
        return Result.success()
    }

}