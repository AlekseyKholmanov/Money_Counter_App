package com.example.holmi_production.money_counter_app.di.modules

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.holmi_production.money_counter_app.interactor.BalanceInteractor
import com.example.holmi_production.money_counter_app.interactor.NotificationInteractor
import com.example.holmi_production.money_counter_app.interactor.SpendingInteractor
import com.example.holmi_production.money_counter_app.storage.BalanceRepository
import com.example.holmi_production.money_counter_app.storage.SettingRepository
import com.example.holmi_production.money_counter_app.storage.SpendingRepository
import com.example.holmi_production.money_counter_app.worker.BalancePopulateTask
import com.example.holmi_production.money_counter_app.worker.BalanceTask
import com.example.holmi_production.money_counter_app.worker.EndMonthTask
import com.example.holmi_production.money_counter_app.worker.NotificationTask

class DaggerWorkerFactory(
    private val settingRepository: SettingRepository,
    private val spendingRepository: SpendingRepository,
    private val balanceRepository: BalanceRepository,
    private val notificationInteractor: NotificationInteractor,
    private val balanceInteractor: BalanceInteractor,
    private val spendingInteractor: SpendingInteractor
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {

        val workerClass = Class.forName(workerClassName).asSubclass(ListenableWorker::class.java)
        val constructor =
            workerClass.getDeclaredConstructor(Context::class.java, WorkerParameters::class.java)
        val instance = constructor.newInstance(appContext, workerParameters)

        when (instance) {
            is EndMonthTask -> injectEndMonthWorker(instance)
            is BalanceTask -> injectBalanceWorker(instance)
            is NotificationTask -> injectNotificationWorker(instance)
            is BalancePopulateTask -> injectBalancePopulateWorker(instance)
        }
        return instance

    }

    private fun injectNotificationWorker(instance: NotificationTask) {
        instance.notificationInteractor = notificationInteractor
    }

    private fun injectBalanceWorker(instance: BalanceTask) {
        instance.spendingRepository = spendingRepository
        instance.balanceRepository = balanceRepository
    }

    private fun injectBalancePopulateWorker(instance: BalancePopulateTask){
        instance.balanceInteractor = balanceInteractor
        instance.spendingInteractor = spendingInteractor
    }

    private fun injectEndMonthWorker(instance: EndMonthTask) {
        instance.settingRepository = settingRepository
    }
}