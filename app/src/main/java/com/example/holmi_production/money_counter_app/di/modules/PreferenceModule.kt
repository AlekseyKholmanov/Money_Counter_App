package com.example.holmi_production.money_counter_app.di.modules

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PreferenceModule {
    companion object{
        val STORAGE_NAME = "PREFERENCE_STORAGE"
    }


    @Provides
    fun provideSharedPreference(context:Context): SharedPreferences {
        return context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE)
    }
}