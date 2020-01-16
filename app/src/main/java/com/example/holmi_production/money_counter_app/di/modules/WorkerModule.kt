package com.example.holmi_production.money_counter_app.di.modules

import android.content.Context
import androidx.work.Configuration
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import com.example.holmi_production.money_counter_app.di.DaggerWorkerFactory
import com.example.holmi_production.money_counter_app.storage.BalanceRepository
import com.example.holmi_production.money_counter_app.storage.PeriodsRepository
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
        periodsRepository: PeriodsRepository
    ): WorkerFactory {
        return DaggerWorkerFactory(
            settingRepository,
            spendingRepository,
            balanceRepository,
            periodsRepository
        )
    }
}