package com.example.holmi_production.money_counter_app.useCases

import com.example.holmi_production.money_counter_app.model.entity.FilterPeriodEntity
import com.example.holmi_production.money_counter_app.model.enums.PeriodType
import org.joda.time.DateTime

interface UpdateActivePeriodUseCase {

    suspend fun updateActivePeriod(from: DateTime, to: DateTime, type: PeriodType)

    suspend fun moveForward(entity: FilterPeriodEntity)

    suspend fun moveBack(entity: FilterPeriodEntity)

}