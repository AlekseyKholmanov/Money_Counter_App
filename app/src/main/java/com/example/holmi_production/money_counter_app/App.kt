package com.example.holmi_production.money_counter_app

import android.app.Application
import androidx.work.WorkerFactory
import com.example.holmi_production.money_counter_app.di.components.ApplicationComponent
import com.example.holmi_production.money_counter_app.di.components.DaggerApplicationComponent
import com.example.holmi_production.money_counter_app.di.modules.ApplicationModule
import com.example.holmi_production.money_counter_app.di.modules.PreferenceModule
import com.example.holmi_production.money_counter_app.storage.BalanceRepository
import com.example.holmi_production.money_counter_app.storage.PeriodsRepository
import com.example.holmi_production.money_counter_app.storage.SettingRepository
import com.example.holmi_production.money_counter_app.storage.SpendingRepository
import javax.inject.Inject


class App: Application(){
    companion object{
        lateinit var component:ApplicationComponent
    }

    @Inject
    lateinit var workerFactory: WorkerFactory

    override fun onCreate() {
        super.onCreate()

        component = DaggerApplicationComponent.builder()
            .application(this)
            .build()
        component.inject(this)
    }
}