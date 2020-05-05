package com.example.holmi_production.money_counter_app.di_copy

import android.app.AlarmManager
import android.app.NotificationManager
import android.content.Context
import android.os.Vibrator
import androidx.core.content.getSystemService
import com.f2prateek.rx.preferences2.RxSharedPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */

val toolsModule = module {
    val STORAGE_NAME = "PREFERENCE_STORAGE"

    single { androidContext().getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE) }
    single { androidContext().getSystemService<AlarmManager>() }
    single { androidContext().getSystemService<NotificationManager>() }
    single { androidContext().getSystemService<Vibrator>() }
    single { RxSharedPreferences.create(get()) }

}