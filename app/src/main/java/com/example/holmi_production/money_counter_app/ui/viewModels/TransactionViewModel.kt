package com.example.holmi_production.money_counter_app.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.holmi_production.money_counter_app.model.Item
import com.example.holmi_production.money_counter_app.model.entity.FilterPeriodEntity
import com.example.holmi_production.money_counter_app.model.uiModels.DatePeriodType
import com.example.holmi_production.money_counter_app.ui.adapter.items.TransactionDayHeaderItem
import com.example.holmi_production.money_counter_app.ui.adapter.items.toItem
import com.example.holmi_production.money_counter_app.useCases.AddActivePeriodUseCase
import com.example.holmi_production.money_counter_app.useCases.EditTransactionUseCase
import com.example.holmi_production.money_counter_app.useCases.GetLatestActivePeriodUseCase
import com.example.holmi_production.money_counter_app.useCases.GetTransactionUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TransactionViewModel(
    private val getTransactionUseCase: GetTransactionUseCase,
    private val editTransactionUSeCase: EditTransactionUseCase,
    private val getLatestActivePeriodUseCase: GetLatestActivePeriodUseCase,
    private val addActivePeriodUseCase: AddActivePeriodUseCase
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
            getLatestActivePeriodUseCase.observeLatestPeriod()
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

    fun updateSelectedPeriod(period: DatePeriodType){

    }

}