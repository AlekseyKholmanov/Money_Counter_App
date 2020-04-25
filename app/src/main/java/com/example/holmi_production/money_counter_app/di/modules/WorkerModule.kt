package com.example.holmi_production.money_counter_app.di.modules

import android.content.Context
import androidx.work.Configuration
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import com.example.holmi_production.money_counter_app.interactor.BalanceInteractor
import com.example.holmi_production.money_counter_app.interactor.NotificationInteractor
import com.example.holmi_production.money_counter_app.interactor.SpendingInteractor
import com.example.holmi_production.money_counter_app.storage.BalanceRepository
import com.example.holmi_production.money_counter_app.storage.SettingRepository
import com.example.holmi_production.money_counter_app.storage.SpendingRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class WorkerModule {

    @Provides
    @Singleton
    fun workManager(context: Context, workerFactory: WorkerFactory): WorkManager {
        val config = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
        WorkManager.initialize(context, config)
        return WorkManager.getInstance(context)
    }

    @Provides
    @Singleton
    fun workerFactory(
        settingRepository: SettingRepository,
        spendingRepository: SpendingRepository,
        balanceRepository: BalanceRepository,
        notificationInteractor: NotificationInteractor,
        balanceInteractor: BalanceInteractor,
        spendingInteractor: SpendingInteractor
    ): WorkerFactory {
        return DaggerWorkerFactory(
            settingRepository,
            spendingRepository,
            balanceRepository,
            notificationInteractor,
            balanceInteractor,
            spendingInteractor
        )
    }

}