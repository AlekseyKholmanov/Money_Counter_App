package com.example.holmi_production.money_counter_app.storage

import android.content.SharedPreferences
import android.util.Log
import com.example.holmi_production.money_counter_app.Test_Singleton
import com.example.holmi_production.money_counter_app.extensions.withNextMonthDate
import com.example.holmi_production.money_counter_app.extensions.withPreviousMonthDate
import com.example.holmi_production.money_counter_app.extensions.withTimeAtEndOfDay
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject
import org.joda.time.DateTime
import org.joda.time.Days
import javax.inject.Inject

class SettingRepository @Inject constructor(private val pref: SharedPreferences) {

    val settingSubject by lazy { PublishSubject.create<Int>() }

    fun setEndMonth(day: Int) {
        Log.d("M_SettingRepository","subject $settingSubject")
        Log.d("M_SettingRepository","repository $this")
        Log.d("M_KeyboardPresenter","test $Test_Singleton")
        settingSubject.onNext(day)
        pref.edit().putInt(END_MONTH, day).apply()
    }

    fun getCurrentPeriodDate(): Pair<DateTime, DateTime> {
        val today = DateTime()
        val endPeriodDay = getEndMonth()
        return if (today.dayOfMonth < endPeriodDay)
            Pair<DateTime, DateTime>(
                DateTime().withPreviousMonthDate().withDayOfMonth(endPeriodDay).withTimeAtStartOfDay(),
                DateTime().withDayOfMonth(endPeriodDay - 1).withTimeAtEndOfDay()
            ) else {
            Pair<DateTime,DateTime>(
                DateTime().withTimeAtStartOfDay().withDayOfMonth(endPeriodDay),
                DateTime().withNextMonthDate().withDayOfMonth(endPeriodDay).withTimeAtEndOfDay()
            )
        }
    }

    fun getDaysToEndPeriod() : Int {
        val days = getCurrentPeriodDate()
        return Days.daysBetween(DateTime.now(), days.second).days
    }

    fun getEndMonth(): Int {
        return pref.getInt(END_MONTH, 15)
    }

    fun observeEndPeriod(): Flowable<Int> {
        Log.d("M_SettingRepository", "observe end date")
        return settingSubject.toFlowable(BackpressureStrategy.LATEST)
    }

    fun getPeriodType(): Int {
        return pref.getInt(PERIOD_TYPE, 0)
    }

    fun setPeriodType(type: Int) {
        pref.edit().putInt(PERIOD_TYPE, type).apply()
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

    fun isOpened(): Boolean {
        return pref.contains(FIRST_OPEN)
    }

    fun getBalancePopulatedStatus(): Boolean {
        return pref.getBoolean(BALANCE_MIGRATION_TAG, false)
    }

    fun setBalancePopulated() {
        pref.edit().putBoolean(BALANCE_MIGRATION_TAG, true).apply()
    }

    companion object {
        val FIRST_OPEN = "FirstOpen"
        val CATEGORY_VALUE = "Category_value"
        val BALANCE_MIGRATION_TAG = "BALANCE_POPULATED"
        val PERIOD_TYPE = "PERIOD_TYPE"
        val END_MONTH = "END_MONTH"
    }
}
