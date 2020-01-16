package com.example.holmi_production.money_counter_app.di

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.holmi_production.money_counter_app.storage.BalanceRepository
import com.example.holmi_production.money_counter_app.storage.PeriodsRepository
import com.example.holmi_production.money_counter_app.storage.SettingRepository
import com.example.holmi_production.money_counter_app.storage.SpendingRepository
import com.example.holmi_production.money_counter_app.worker.EndMonthTask
import java.lang.Exception

class DaggerWorkerFactory(
    private val settingRepository: SettingRepository,
    private val spendingRepository: SpendingRepository,
    private val balanceRepository: BalanceRepository,
    private val periodsRepository: PeriodsRepository
):WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters): ListenableWorker? {
        val workerClass = Class.forName(workerClassName).asSubclass(ListenableWorker::class.java)
        val constructor = workerClass.getDeclaredConstructor(Context::class.java, WorkerParameters::class.java)
        val instance = constructor.newInstance(appContext, workerParameters)

        when (instance) {
            is EndMonthTask -> injectEndMonthTask(instance)
            else -> throw Exception("No injection required for worker $workerClassName")
        }

        return instance
    }

    private fun injectEndMonthTask(worker: EndMonthTask) {
        worker.settingRepository = settingRepository
    }
}