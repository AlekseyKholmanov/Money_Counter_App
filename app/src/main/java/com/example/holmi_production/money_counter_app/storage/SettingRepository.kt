package com.example.holmi_production.money_counter_app.storage

import android.content.SharedPreferences
import android.util.Log
import com.example.holmi_production.money_counter_app.async
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject
import org.joda.time.DateTime
import org.joda.time.Days
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingRepository @Inject constructor(private val pref: SharedPreferences) {
    private val settingSubject by lazy {
        Log.d("qwerty", this.hashCode().toString())
        PublishSubject.create<Long>()
    }

    fun setAppOpened() {
        pref.edit().putBoolean(FIRST_OPEN, true).apply()
    }

    fun saveStartDate(startDay: DateTime) {
        pref.edit().putLong(START_PERIOD, startDay.millis).apply()
    }

    fun saveEndDate(endDate: DateTime) {
        Log.d("qwerty", "saved end date")
        settingSubject.onNext(endDate.millis)
        pref.edit().putLong(END_PERIOD, endDate.millis).apply()
    }

    fun isOpened(): Boolean {
        return pref.contains(FIRST_OPEN)
    }

    private fun getEndPeriod(): Long {
        return pref.getLong(END_PERIOD, 0)
    }

    fun getTillEnd(): Int {
        val endDate = getEndPeriod()
        return Days.daysBetween(DateTime().withTimeAtStartOfDay(), DateTime(endDate)).days + 1
    }

    fun observeEndDate(): Flowable<Long> {
        Log.d("qwerty", "observe end date")
        return settingSubject.toFlowable(BackpressureStrategy.LATEST).async()
    }

    val FIRST_OPEN = "FirstOpen"
    val START_PERIOD = "START_PERIOD"
    val END_PERIOD = "END_PERIOD"
}