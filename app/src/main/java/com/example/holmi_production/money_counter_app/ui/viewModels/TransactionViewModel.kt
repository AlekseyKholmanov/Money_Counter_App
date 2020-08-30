package com.example.holmi_production.money_counter_app.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.holmi_production.money_counter_app.extensions.withTimeAtEndOfDay
import com.example.holmi_production.money_counter_app.extensions.withTimeAtEndOfMonth
import com.example.holmi_production.money_counter_app.extensions.withTimeAtEndOfYear
import com.example.holmi_production.money_counter_app.model.Item
import com.example.holmi_production.money_counter_app.model.entity.FilterPeriodEntity
import com.example.holmi_production.money_counter_app.model.enums.PeriodType
import com.example.holmi_production.money_counter_app.ui.adapter.items.TransactionDayHeaderItem
import com.example.holmi_production.money_counter_app.ui.adapter.items.toItem
import com.example.holmi_production.money_counter_app.useCases.UpdateActivePeriodUseCase
import com.example.holmi_production.money_counter_app.useCases.EditTransactionUseCase
import com.example.holmi_production.money_counter_app.useCases.GetActivePeriodUseCase
import com.example.holmi_production.money_counter_app.useCases.GetTransactionUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.joda.time.DateTime

class TransactionViewModel(
    private val getTransactionUseCase: GetTransactionUseCase,
    private val editTransactionUSeCase: EditTransactionUseCase,
    private val getActivePeriodUseCase: GetActivePeriodUseCase,
    private val updateActivePeriodUseCase: UpdateActivePeriodUseCase
) : ViewModel() {

    private val _transactions = MutableLiveData<List<Item>>()
    val transactions: LiveData<List<Item>> = _transactions

    private val _activePeriod = MutableLiveData<FilterPeriodEntity>()
    val activePeriod: LiveData<FilterPeriodEntity> = _activePeriod

    private val _summary = MutableLiveData<Pair<Double, Double>>()
    val summary: LiveData<Pair<Double, Double>> = _summary

    fun observeTransaction() {
        viewModelScope.launch {
            getTransactionUseCase.observeTransactionsDetails()
                .onEach {
                    val divided = it.partition { it.transaction.sum >= 0 }
                    withContext(Dispatchers.Main) {
                        _summary.value =
                            divided.first.sumByDouble { it.transaction.sum } to divided.second.sumByDouble { it.transaction.sum }
                    }
                }
                .map {
                    val grouped = it.groupBy { it.transaction.createdDate.withTimeAtStartOfDay() }
                    val items = mutableListOf<Item>()
                    grouped.forEach { (dateTime, list) ->
                        items.add(
                            TransactionDayHeaderItem(
                                dateTime,
                                list.sumByDouble { it.transaction.sum })
                        )
                        items.addAll(list.map { it.toItem() })
                    }
                    items
                }
                .flowOn(Dispatchers.IO)
                .collect {
                    _transactions.value = it
                }
        }
    }

    fun observeActivePeriod() {
        viewModelScope.launch {
            getActivePeriodUseCase.observeLatestPeriod()
                .flowOn(Dispatchers.IO)
                .collect {
                    _activePeriod.value = it
                }

        }
    }

    fun deleteTransaction(transactionId: String) {
        viewModelScope.launch {
            editTransactionUSeCase.deleteTransaction(transactionId)
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

    fun moveDateForward() {
        viewModelScope.launch {
            activePeriod.value?.let {
                updateActivePeriodUseCase.moveForward(it)
            }
        }
    }

    fun moveDateBack() {
        viewModelScope.launch {
            activePeriod.value?.let {
                updateActivePeriodUseCase.moveBack(it)
            }
        }
    }

}