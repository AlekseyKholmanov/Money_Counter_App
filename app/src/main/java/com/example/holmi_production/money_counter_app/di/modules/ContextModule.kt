package com.example.holmi_production.money_counter_app.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextModule(private val context: Context){

    @Provides
    @Singleton
    fun provideApplication(): Context = context
}