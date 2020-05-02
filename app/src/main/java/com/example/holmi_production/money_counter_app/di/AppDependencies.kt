package com.example.holmi_production.money_counter_app.di

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.WallpaperManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Vibrator
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import com.example.holmi_production.money_counter_app.orm.ExpenseDatabase

interface AppDependencies {

    fun context(): Context

    fun appPrefs(): SharedPreferences

    fun db(): ExpenseDatabase

    fun alarmManager(): AlarmManager

    fun notificationManager(): NotificationManager

    fun vibrator(): Vibrator

}
