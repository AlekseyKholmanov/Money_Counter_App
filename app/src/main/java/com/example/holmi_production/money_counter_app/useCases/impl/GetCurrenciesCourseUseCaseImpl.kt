package com.example.holmi_production.money_counter_app.useCases.impl

import com.example.holmi_production.money_counter_app.model.dto.CurrencyCourseDTO
import com.example.holmi_production.money_counter_app.model.enums.CurrencyType
import com.example.holmi_production.money_counter_app.storage.db.CurrencyDatabase
import com.example.holmi_production.money_counter_app.useCases.GetCurrenciesCourseUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

class GetCurrenciesCourseUseCaseImpl(
    private val db: CurrencyDatabase
) : GetCurrenciesCourseUseCase {

    override fun observeCourses(): Flow<List<CurrencyCourseDTO>> {
        return db.getAll()
            .filterNotNull()
            .map {
            it.map {
                CurrencyCourseDTO(
                    date = it.dateId,
                    courses = mapOf(
                        CurrencyType.DOLLAR to it.usd,
                        CurrencyType.RUBBLE to it.ruble,
                        CurrencyType.EURO to it.euro,
                        CurrencyType.SHACKEL to it.shakel
                    )
                )
            }
        }
    }
}