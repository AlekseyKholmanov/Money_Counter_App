package com.example.holmi_production.money_counter_app.di.modules

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.holmi_production.money_counter_app.storage.BalanceRepository
import com.example.holmi_production.money_counter_app.storage.SettingRepository
import com.example.holmi_production.money_counter_app.storage.SpendingRepository
import com.example.holmi_production.money_counter_app.worker.EndMonthTask

class DaggerWorkerFactory(
    private val settingRepository: SettingRepository,
    private val spendingRepository: SpendingRepository,
    private val balanceRepository: BalanceRepository
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        val workerClass = Class.forName(workerClassName).asSubclass(ListenableWorker::class.java)
        val constructor = workerClass.getDeclaredConstructor(Context::class.java, WorkerParameters::class.java)
        val instance = constructor.newInstance(appContext, workerParameters)
        when(instance) {
            is EndMonthTask -> injectEndTask(instance)
        }
        return instance
    }

    private fun injectEndTask(worker: EndMonthTask) {
        worker.settingRepository = settingRepository
    }
}