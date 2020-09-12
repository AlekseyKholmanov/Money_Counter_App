package com.example.holmi_production.money_counter_app.storage.db

import android.content.SharedPreferences


class AppPreference(
    private val pref: SharedPreferences,
) {
    companion object {
        const val IS_ONBOARDING_COMPLETED = "is onboarding completed"
    }

    var isOnboardingCompleted: Boolean
        get() = pref.getBoolean(IS_ONBOARDING_COMPLETED, false)
        set(value) {
            pref.edit().putBoolean(IS_ONBOARDING_COMPLETED, value).commit()
        }
}

