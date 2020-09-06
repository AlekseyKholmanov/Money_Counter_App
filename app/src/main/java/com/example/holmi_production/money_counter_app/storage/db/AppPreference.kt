package com.example.holmi_production.money_counter_app.storage.db

import android.content.SharedPreferences
import android.util.Log
import com.example.holmi_production.money_counter_app.extensions.withNextMonthDate
import com.example.holmi_production.money_counter_app.extensions.withPreviousMonthDate
import com.example.holmi_production.money_counter_app.extensions.withTimeAtEndOfDay
import org.joda.time.DateTime
import org.joda.time.Days


class AppPreference(
    private val pref: SharedPreferences,
) {


    var sumPerDayOption: Boolean
        get() = pref.getBoolean(OPTION_SUM_PER_DAY, true)
        set(value) {
            pref.edit().putBoolean(OPTION_SUM_PER_DAY, value).apply()
        }

    var isConverterEnabled: Boolean
        get() = pref.getBoolean(Converter, false)
        set(value) {
            pref.edit().putBoolean(Converter, value).apply()
        }

    var isOnboardingCompleted: Boolean
        get() = pref.getBoolean(IS_ONBOARDING_COMPLETED, false)
        set(value) {
            pref.edit().putBoolean(IS_ONBOARDING_COMPLETED, value).commit()
        }

    fun getCurrentPeriods(): Pair<DateTime, DateTime> {
        val today = DateTime()
        val endPeriodDay = getEndMonth()
        return if (today.dayOfMonth < endPeriodDay)
            Pair<DateTime, DateTime>(
                DateTime().withPreviousMonthDate().withDayOfMonth(endPeriodDay)
                    .withTimeAtStartOfDay(),
                DateTime().withDayOfMonth(endPeriodDay - 1).withTimeAtEndOfDay()
            ) else {
            Pair<DateTime, DateTime>(
                DateTime().withTimeAtStartOfDay().withDayOfMonth(endPeriodDay),
                DateTime().withNextMonthDate().withDayOfMonth(endPeriodDay).withTimeAtEndOfDay()
            )
        }
    }

    fun getDaysToEndPeriod(): Int {
        val days = getCurrentPeriods()
        return Days.daysBetween(DateTime.now(), days.second).days
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

    fun setConverterValue(value: String) {
        pref.edit().putString(Converter_VALUE, value).apply()
    }
    companion object {
        const val IS_ONBOARDING_COMPLETED = "is onboarding completed"
        val Converter_VALUE = "Converter_VALUE"
        val Converter = "Converter"
        val FIRST_OPEN = "FirstOpen"
        val BALANCE_MIGRATION_TAG = "BALANCE_POPULATED"
        val PERIOD_TYPE = "PERIOD_TYPE"
        val END_MONTH = "END_MONTH"
        val OPTION_SUM_PER_DAY = "SUM_PER_DAY"
    }
}
