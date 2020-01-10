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

    companion object {
        val FIRST_OPEN = "FirstOpen"
        val START_PERIOD = "START_PERIOD"
        val END_PERIOD = "END_PERIOD"
        val IS_END = "IS_END"
        val CATEGORY_VALUE = "Category_value"
        val BALANCE_MIGRATION_TAG = "BALANCE_POPULATED"
        val PERIOD_TYPE = "PERIOD_TYPE"
        val END_MONTH = "END_MONTH"
    }

    fun setEndMonth(day: Int) {
        pref.edit().putInt(END_MONTH, day).apply()
    }

    fun getEndMonth(): Int {
        return pref.getInt(END_MONTH, 15)
    }

    fun getPeriodType(): Int {
        return pref.getInt(PERIOD_TYPE, 0)
    }

    fun setPeriodType(type: Int) {
        pref.edit().putInt(PERIOD_TYPE, type).apply()
    }

    private val settingSubject by lazy {
        PublishSubject.create<Long>()
    }

    fun setCategoryButtonType(type: Int) {
        pref.edit().putInt(CATEGORY_VALUE, type).apply()
    }

    fun getCategoryValue(): Int {
        return pref.getInt(CATEGORY_VALUE, 8)
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

    fun getStartDate(): Long {
        return pref.getLong(START_PERIOD, 0)
    }

    fun getEndPeriod(): Long {
        return pref.getLong(END_PERIOD, 0)
    }

    fun getTillEnd(): Int {
        val endDate = getEndPeriod()
        return Days.daysBetween(DateTime().withTimeAtStartOfDay(), DateTime(endDate)).days
    }

    fun observeEndDate(): Flowable<Long> {
        Log.d("qwerty", "observe end date")
        return settingSubject.toFlowable(BackpressureStrategy.LATEST)
    }

    fun setIsEnd(isEnd: Boolean) {
        pref.edit().putBoolean(IS_END, isEnd).apply()
    }

    fun getIsEnd(): Boolean {
        return pref.getBoolean(IS_END, false)
    }

    fun getBalancePopulatedStatus(): Boolean {
        return pref.getBoolean(BALANCE_MIGRATION_TAG, false)
    }

    fun setBalancePopulated() {
        pref.edit().putBoolean(BALANCE_MIGRATION_TAG, true).apply()
    }
}