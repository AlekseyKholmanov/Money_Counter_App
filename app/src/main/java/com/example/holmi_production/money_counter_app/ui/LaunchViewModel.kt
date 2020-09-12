/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.holmi_production.money_counter_app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.holmi_production.money_counter_app.extensions.Event
import com.example.holmi_production.money_counter_app.useCases.OnboardingCompletedUseCase

/**
 * Logic for determining which screen to send users to on app launch.
 */
class LaunchViewModel(
    onboardingCompletedUseCase: OnboardingCompletedUseCase
) : ViewModel() {

    val launchDestination = liveData {
        val result = onboardingCompletedUseCase.isOnboardingComplete()

        if (result) {
            emit(Event(LaunchDestination.MAIN_ACTIVITY))
        } else {
            emit(Event(LaunchDestination.ONBOARDING))
        }
    }
}

enum class LaunchDestination {
    ONBOARDING,
    MAIN_ACTIVITY
}
