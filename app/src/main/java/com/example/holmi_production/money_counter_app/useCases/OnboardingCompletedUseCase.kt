package com.example.holmi_production.money_counter_app.useCases

interface OnboardingCompletedUseCase {

    fun isOnboardingComplete(): Boolean

    fun setOnboardingComplete(value:Boolean)
}
