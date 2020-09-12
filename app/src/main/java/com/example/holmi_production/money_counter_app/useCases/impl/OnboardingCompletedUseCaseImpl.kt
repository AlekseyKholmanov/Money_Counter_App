package com.example.holmi_production.money_counter_app.useCases.impl

import com.example.holmi_production.money_counter_app.storage.db.AppPreference
import com.example.holmi_production.money_counter_app.useCases.OnboardingCompletedUseCase

class OnboardingCompletedUseCaseImpl(
    private val appPreference: AppPreference
) : OnboardingCompletedUseCase {

    override fun isOnboardingComplete(): Boolean {
        return appPreference.isOnboardingCompleted
    }

    override fun setOnboardingComplete(value: Boolean) {
        appPreference.isOnboardingCompleted = true
    }


}