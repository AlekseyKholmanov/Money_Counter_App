package com.example.holmi_production.money_counter_app.storage

import android.content.SharedPreferences
import android.util.Log
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
        PublishSubject.create<Long>()
    }

    private val isEndSubject by lazy {
        PublishSubject.create<Boolean>()
    }

    private val categorySubject by lazy {
        PublishSubject.create<Int>()
    }

    fun setEndPeriod() {
        pref.edit().putBoolean(IS_END, true).apply()
        isEndSubject.onNext(true)
    }

    fun unsetEndPeriod() {
        pref.edit().putBoolean(IS_END, false).apply()
        isEndSubject.onNext(false)
    }

    fun setCategoryButtonType(type: Int) {
        pref.edit().putInt(CATEGORY_VALUE, type).apply()
        categorySubject.onNext(type)
    }

    fun getCategoryValue(): Int {
        return pref.getInt(CATEGORY_VALUE, 8)
    }

    fun observeCategoryValue(): Flowable<Int> {
        return categorySubject.toFlowable(BackpressureStrategy.LATEST)
            .distinctUntilChanged()
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
        return Days.daysBetween(DateTime().withTimeAtStartOfDay(), DateTime(endDate)).days
    }

    fun observeEndPeriod(): Flowable<Boolean> {
        return isEndSubject.toFlowable(BackpressureStrategy.LATEST)
    }

    fun observeEndDate(): Flowable<Long> {
        Log.d("qwerty", "observe end date")
        return settingSubject.toFlowable(BackpressureStrategy.LATEST)
    }

    val FIRST_OPEN = "FirstOpen"
    val START_PERIOD = "START_PERIOD"
    val END_PERIOD = "END_PERIOD"
    val IS_END = "IS_END"
    val CATEGORY_VALUE = "Category_value"
}