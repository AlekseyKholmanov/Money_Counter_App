package com.example.holmi_production.money_counter_app.di

import android.app.AlarmManager
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Vibrator
import com.example.holmi_production.money_counter_app.orm.ExpenseDatabase
import com.f2prateek.rx.preferences2.RxSharedPreferences

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
interface CoreTools {

    val context: Context

    val appPrefs: SharedPreferences

    val rxPrefs: RxSharedPreferences

    val db: ExpenseDatabase

    val alarmManager: AlarmManager

    val notificationManager: NotificationManager

    val vibrator: Vibrator
}