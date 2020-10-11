package com.example.holmi_production.money_counter_app

import android.app.Application
import com.example.holmi_production.money_counter_app.di.appComponent
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initCoin()
        registerActivityLifecycleCallbacks(ActivityLifecycleCallback())
    }

    private fun initCoin() {
        startKoin {
            androidContext(this@App)
            modules(appComponent)
        }
    }
}