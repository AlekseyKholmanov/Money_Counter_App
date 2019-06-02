package com.example.holmi_production.money_counter_app

import android.app.Application
import com.example.holmi_production.money_counter_app.di.components.ApplicationComponent
import com.example.holmi_production.money_counter_app.di.components.DaggerApplicationComponent
import com.example.holmi_production.money_counter_app.di.modules.ApplicationModule
import com.example.holmi_production.money_counter_app.di.modules.ContextModule

class App:Application(){
    companion object{
        lateinit var component:ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        initDi()
    }

    private fun initDi() {
        component = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .contextModule(ContextModule(this))
            .build()
    }
}

//TODO отписка от интерфейсов