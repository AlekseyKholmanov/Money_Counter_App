package com.example.holmi_production.money_counter_app.useCases

import com.example.holmi_production.money_counter_app.model.dto.CurrencyCourseDTO
import kotlinx.coroutines.flow.Flow

interface GetCurrenciesCourseUseCase {

    fun observeCourses(): Flow<List<CurrencyCourseDTO>>
}