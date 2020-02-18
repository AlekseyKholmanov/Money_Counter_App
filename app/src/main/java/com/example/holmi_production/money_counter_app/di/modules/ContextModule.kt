package com.example.holmi_production.money_counter_app.di.modules

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class ContextModule {

    @Singleton
    @Binds
    abstract fun provideApplication(application: Application) :Context
}