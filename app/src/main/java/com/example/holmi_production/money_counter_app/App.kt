package com.example.holmi_production.money_counter_app

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import com.example.holmi_production.money_counter_app.di.components.ApplicationComponent
import com.example.holmi_production.money_counter_app.di.components.DaggerApplicationComponent
import com.example.holmi_production.money_counter_app.di.modules.ApplicationModule
import com.example.holmi_production.money_counter_app.di.modules.ContextModule
import com.example.holmi_production.money_counter_app.di.modules.PreferenceModule

class App:Application(){
    companion object{
        lateinit var component:ApplicationComponent
    }

//    @Inject
//    lateinit var workerFactory: SampleWorkerFactory

    override fun onCreate() {
        super.onCreate()
        initDi()
//        val factory = DaggerDIComponent.create().factory()
//        WorkManager.initialize(this, Configuration.Builder().setWorkerFactory(factory).build())
    }

    private fun initDi() {
        component = DaggerApplicationComponent.create()
    }
}