package com.example.holmi_production.money_counter_app.worker

import android.content.Context
import android.util.Log
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.holmi_production.money_counter_app.di.modules.ChildWorkerFactory
import com.example.holmi_production.money_counter_app.storage.SettingRepository
import javax.inject.Inject
import javax.inject.Provider

class EndMonthTask(
    private val appContext: Context,
    private val params: WorkerParameters,
    private val settingRepository: SettingRepository
) : Worker(appContext, params) {

    override fun doWork(): Result {
        Log.d("M_EndMonthTask", "Start")
        val value = inputData.getInt("endMonth", 1)
//        val pref = context.getSharedPreferences(PreferenceModule.STORAGE_NAME, Context.MODE_PRIVATE)
        settingRepository.setEndMonth(value)
        Log.d("M_EndMonthTask", "complete $value")
//        pref.edit().putInt(SettingRepository.END_MONTH, value).apply()
        return Result.success()
    }

    class  Factory @Inject constructor(
        private val settingRepository: Provider<SettingRepository>
    ):ChildWorkerFactory {
        override fun create(appContext: Context, params: WorkerParameters): ListenableWorker {
            return  EndMonthTask(appContext, params, settingRepository.get())
        }
    }
}

