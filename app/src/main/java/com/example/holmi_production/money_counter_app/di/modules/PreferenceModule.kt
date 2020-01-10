package com.example.holmi_production.money_counter_app.di.modules

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PreferenceModule(private val context: Context) {
    companion object{
        val STORAGE_NAME = "PREFERENCE_STORAGE"
    }


    @Provides
    @Singleton
    fun provideSharedPreference(): SharedPreferences {
        return context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE)
    }
}