package com.example.holmi_production.money_counter_app.di.modules

import android.app.AlarmManager
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Vibrator
import androidx.core.content.getSystemService
import com.f2prateek.rx.preferences2.RxSharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
@Module
object CoreModule {

    const val STORAGE_NAME = "PREFERENCE_STORAGE"

    @Provides
    @Singleton
    fun provideSharedPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideRXSharedPreference(sharedPreferences: SharedPreferences): RxSharedPreferences {
        return RxSharedPreferences.create(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideAlarmManager(context: Context): AlarmManager {
        return context.applicationContext.getSystemService()!!
    }

    @Provides
    @Singleton
    fun provideNotificationManager(context: Context): NotificationManager {
        return context.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    @Provides
    @Singleton
    fun provideVibrator(context: Context): Vibrator {
        return context.applicationContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

}