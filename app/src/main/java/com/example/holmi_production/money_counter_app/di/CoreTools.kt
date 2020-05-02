package com.example.holmi_production.money_counter_app.di

import android.app.AlarmManager
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Vibrator
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import com.example.holmi_production.money_counter_app.orm.ExpenseDatabase

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
interface CoreTools {

    val context: Context

    val appPrefs: SharedPreferences

    val db: ExpenseDatabase

    val alarmManager: AlarmManager

    val notificationManager: NotificationManager

    val vibrator: Vibrator
}