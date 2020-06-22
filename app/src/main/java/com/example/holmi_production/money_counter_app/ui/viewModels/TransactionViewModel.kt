package com.example.holmi_production.money_counter_app.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.holmi_production.money_counter_app.model.Item
import com.example.holmi_production.money_counter_app.ui.adapter.items.TransactionDayHeaderItem
import com.example.holmi_production.money_counter_app.ui.adapter.items.TransactionItem
import com.example.holmi_production.money_counter_app.ui.adapter.items.toItem
import com.example.holmi_production.money_counter_app.useCases.EditTransactionUseCase
import com.example.holmi_production.money_counter_app.useCases.GetTransactionUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class TransactionViewModel(
    private val getTransactionUseCase: GetTransactionUseCase,
    private val editTransactionUSeCase: EditTransactionUseCase
) : ViewModel() {

    private val _transactions = MutableLiveData<List<Item>>()
    val transactions: LiveData<List<Item>> = _transactions

    fun observeTransaction() {
        viewModelScope.launch {
            getTransactionUseCase.observeTransactionsDetails()
                .map {
                    val grouped = it.groupBy { it.transaction.createdDate.withTimeAtStartOfDay() }
                    val items = mutableListOf<Item>()
                    grouped.forEach { (dateTime, list) ->
                        items.add(TransactionDayHeaderItem(dateTime, list.sumByDouble { it.transaction.sum }))
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

    fun deleteTransaction(transactionId: String){
        viewModelScope.launch {
            editTransactionUSeCase.deleteTransaction(transactionId)
        }
    }

}