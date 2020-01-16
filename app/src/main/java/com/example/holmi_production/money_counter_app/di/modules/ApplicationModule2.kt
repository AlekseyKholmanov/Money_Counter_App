package com.example.holmi_production.money_counter_app.di.modules

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ApplicationModule2 {

    @Singleton
    @Binds
    abstract fun bindContext(application: Application): Context

}