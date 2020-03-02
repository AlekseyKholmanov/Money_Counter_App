package com.example.holmi_production.money_counter_app

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import com.example.holmi_production.money_counter_app.di.components.ApplicationComponent
import com.example.holmi_production.money_counter_app.di.components.DaggerApplicationComponent
import com.example.holmi_production.money_counter_app.di.modules.ContextModule


class App : Application() {
    companion object {
        lateinit var component: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        initDi()
        initWorker()
    }

    private fun initWorker() {
        val factory = component.factory()
        WorkManager.initialize(
            this, Configuration.Builder()
                .setMinimumLoggingLevel(android.util.Log.DEBUG).setWorkerFactory(factory).build()
        )
    }

    private fun initDi() {
        component = DaggerApplicationComponent.builder()
            .contextModule(ContextModule(this)).build()
    }
}