package com.example.holmi_production.money_counter_app.useCases.impl

import com.example.holmi_production.money_counter_app.extensions.withTimeAtEndOfDay
import com.example.holmi_production.money_counter_app.extensions.withTimeAtEndOfMonth
import com.example.holmi_production.money_counter_app.extensions.withTimeAtEndOfYear
import com.example.holmi_production.money_counter_app.model.entity.FilterPeriodEntity
import com.example.holmi_production.money_counter_app.model.enums.PeriodType
import com.example.holmi_production.money_counter_app.storage.PeriodsDatabase
import com.example.holmi_production.money_counter_app.useCases.UpdateActivePeriodUseCase
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.LocalDateTime

class UpdateActivePeriodUseCaseImpl(
    private val periodsDatabase: PeriodsDatabase
) : UpdateActivePeriodUseCase {

    override suspend fun updateActivePeriod(from: DateTime, to: DateTime, type: PeriodType) {
        withContext(Dispatchers.IO) {
            periodsDatabase.addPeriod(
                left = from.withTimeAtStartOfDay(),
                right = to.withTimeAtEndOfDay(),
                type = type
            )
        }
    }

    override suspend fun moveForward(entity: FilterPeriodEntity) = withContext(Dispatchers.IO)
    {
        val pair: Pair<DateTime, DateTime> = when (entity.type) {
            PeriodType.DAY -> {
                entity.from.plusDays(1) to entity.from.plusDays(1)
            }
            PeriodType.WEEK -> {
                val diff = Days.daysBetween(
                    entity.from,
                    entity.to
                ).days + 1
                entity.from.plusDays(diff) to entity.to.plusDays(diff)
            }
            PeriodType.MONTH -> {
                val firstDayNextMonth = entity.from.plusMonths(1)
                val endDay = firstDayNextMonth.withTimeAtEndOfMonth()
                firstDayNextMonth to endDay
            }
            PeriodType.YEAR -> {
                val newYear = entity.from.plusYears(1)
                val endOfYear = newYear.withTimeAtEndOfYear()
                newYear to endOfYear
            }
            PeriodType.CUSTOM -> {
                val diff = Days.daysBetween(entity.from, entity.to).days + 1
                entity.from.plusDays(diff) to entity.to.plusDays(diff)
            }
        }
        periodsDatabase.addPeriod(
            left = pair.first,
            right = pair.second,
            type = entity.type
        )
    }

    override suspend fun moveBack(entity: FilterPeriodEntity) {
        val pair: Pair<DateTime, DateTime> = when (entity.type) {
            PeriodType.DAY -> {
                entity.from.minusDays(1) to entity.from.minusDays(1)
            }
            PeriodType.WEEK -> {
                val diff = Days.daysBetween(
                    entity.from,
                    entity.to
                ).days - 1
                entity.from.minusDays(diff) to entity.to.minusDays(diff)
            }
            PeriodType.MONTH -> {
                val firstDayNextMonth = entity.from.minusMonths(1)
                val endDay = firstDayNextMonth.withTimeAtEndOfMonth()
                firstDayNextMonth to endDay
            }
            PeriodType.YEAR -> {
                val newYear = entity.from.minusYears(1)
                val endOfYear = newYear.withTimeAtEndOfYear()
                newYear to endOfYear
            }
            PeriodType.CUSTOM -> {
                val diff = Days.daysBetween(entity.from, entity.to).days + 1
                entity.from.minusDays(diff) to entity.to.minusDays(diff)
            }
        }
        periodsDatabase.addPeriod(
            left = pair.first,
            right = pair.second,
            type = entity.type
        )
    }
}