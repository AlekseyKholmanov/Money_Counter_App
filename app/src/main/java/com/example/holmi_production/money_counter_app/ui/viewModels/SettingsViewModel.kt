package com.example.holmi_production.money_counter_app.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.holmi_production.money_counter_app.useCases.GetTransactionUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SettingsViewModel(
    private val getTransactionUseCase: GetTransactionUseCase
): ViewModel() {

    private val _transactionCount = MutableLiveData<Int>()
    val transactionCount: LiveData<Int> = _transactionCount

    init{
        getTransactionUseCase
            .observeTransactionCount()
            .flowOn(Dispatchers.IO)
            .onEach {
                _transactionCount.value = it
            }
            .launchIn(viewModelScope)
    }
}