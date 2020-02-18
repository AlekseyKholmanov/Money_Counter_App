package com.example.holmi_production.money_counter_app.di.modules

import android.app.AlarmManager
import android.app.Application
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Context.VIBRATOR_SERVICE
import android.os.Vibrator
import androidx.core.content.getSystemService
import androidx.room.Room
import com.example.holmi_production.money_counter_app.orm.ExpenseDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule {
    @Provides
    @Singleton
    fun provideAlarmManager(context: Context): AlarmManager {
        return context.applicationContext.getSystemService()!!
    }

    @Provides
    @Singleton
    fun provideNotificationManager(context: Context):NotificationManager{
        return  context.applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }

    @Provides
    @Singleton
    fun provideVibrator(context: Context): Vibrator {
        return context.applicationContext.getSystemService(VIBRATOR_SERVICE) as Vibrator
    }
}