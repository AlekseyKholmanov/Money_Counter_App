package com.example.holmi_production.money_counter_app.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.holmi_production.money_counter_app.storage.SettingRepository
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

class EndMonthTask @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted private val params: WorkerParameters,
    private val settingRepository: SettingRepository) : Worker(appContext, params) {

    override fun doWork(): Result {
        val value = inputData.getInt("endMonth", 1)
//        val pref = context.getSharedPreferences(PreferenceModule.STORAGE_NAME, Context.MODE_PRIVATE)
        settingRepository.setEndPeriod(value)
//        pref.edit().putInt(SettingRepository.END_MONTH, value).apply()
        return Result.success()
    }

    @AssistedInject.Factory
    interface Factory : ChildWorkerFactory
}

