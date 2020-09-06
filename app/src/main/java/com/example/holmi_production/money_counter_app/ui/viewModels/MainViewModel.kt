package com.example.holmi_production.money_counter_app.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.holmi_production.money_counter_app.extensions.withTimeAtEndOfDay
import com.example.holmi_production.money_counter_app.extensions.withTimeAtEndOfMonth
import com.example.holmi_production.money_counter_app.extensions.withTimeAtEndOfYear
import com.example.holmi_production.money_counter_app.model.entity.FilterPeriodEntity
import com.example.holmi_production.money_counter_app.model.enums.PeriodType
import com.example.holmi_production.money_counter_app.useCases.GetActivePeriodUseCase
import com.example.holmi_production.money_counter_app.useCases.UpdateActivePeriodUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import org.joda.time.DateTime

class MainViewModel(
    private val getActivePeriodUseCase: GetActivePeriodUseCase,
    private val updateActivePeriodUseCase: UpdateActivePeriodUseCase
): ViewModel() {

    private val _activePeriod = MutableLiveData<FilterPeriodEntity>()
    val activePeriod: LiveData<FilterPeriodEntity> = _activePeriod

    init{
        viewModelScope.launch {
            getActivePeriodUseCase.observeLatestPeriod()
                .flowOn(Dispatchers.IO)
                .collect {
                    _activePeriod.value = it
                }
        }
    }



    fun updateSelectedPeriod(period: PeriodType, from: DateTime? = null, to: DateTime? = null) {
        val periodType = when (period) {
            PeriodType.DAY -> {
                FilterPeriodEntity(
                    from = DateTime.now().withTimeAtStartOfDay(),
                    to = DateTime.now().withTimeAtEndOfDay()
                )
            }
            PeriodType.WEEK ->
                FilterPeriodEntity(
                    from = DateTime.now().withTimeAtStartOfDay().withDayOfWeek(1),
                    to = DateTime.now().withTimeAtEndOfDay().withDayOfWeek(7)
                )
            PeriodType.MONTH -> {
                FilterPeriodEntity(
                    from = DateTime.now().withDayOfMonth(1).withTimeAtStartOfDay(),
                    to = DateTime.now().withTimeAtEndOfMonth()
                        .withTimeAtEndOfDay()
                )
            }
            PeriodType.YEAR -> {
                FilterPeriodEntity(
                    from = DateTime.now().withDayOfYear(1).withTimeAtStartOfDay(),
                    to = DateTime.now().withTimeAtEndOfYear().withTimeAtEndOfDay()
                )
            }
            PeriodType.CUSTOM -> {
                FilterPeriodEntity(
                    from = from!!.withTimeAtStartOfDay(),
                    to = to!!.withTimeAtEndOfDay()
                )
            }
        }
        viewModelScope.launch {
            updateActivePeriodUseCase.updateActivePeriod(periodType.from, periodType.to, period)
        }
    }

}