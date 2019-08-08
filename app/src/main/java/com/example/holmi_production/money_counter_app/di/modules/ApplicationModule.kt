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
class ApplicationModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Application = application


    @Provides
    @Singleton
    fun provideDatabase(context: Context): ExpenseDatabase {
        return ExpenseDatabase.getInstance(context)!!
    }

    @Provides
    @Singleton
    fun provideAlarmManager(): AlarmManager {
        return application.getSystemService<AlarmManager>()!!
    }

    @Provides
    @Singleton
    fun provideNotificationManager():NotificationManager{
        return  application.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }

    @Provides
    @Singleton
    fun provideVibrator(): Vibrator {
        return application.getSystemService(VIBRATOR_SERVICE) as Vibrator
    }
}