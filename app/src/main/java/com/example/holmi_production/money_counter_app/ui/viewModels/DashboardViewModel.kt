package com.example.holmi_production.money_counter_app.ui.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.holmi_production.money_counter_app.model.AccountDetails
import com.example.holmi_production.money_counter_app.model.uiModels.DashboardAccountDetails
import com.example.holmi_production.money_counter_app.storage.data_store.SettingManager
import com.example.holmi_production.money_counter_app.ui.adapter.items.AccountInfoItem
import com.example.holmi_production.money_counter_app.ui.adapter.items.toInfo
import com.example.holmi_production.money_counter_app.useCases.GetAccountsUseCase
import com.example.holmi_production.money_counter_app.useCases.GetTransactionUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
class DashboardViewModel(
    private val getAccountsUseCase: GetAccountsUseCase,
    private val getTransactionUseCase: GetTransactionUseCase,
    private val settingsManager: SettingManager
) : ViewModel() {

    private val _accountDetails = MutableLiveData<DashboardAccountDetails>()
    val accountDetails: LiveData<DashboardAccountDetails> = _accountDetails

    init {
        viewModelScope.launch {
            settingsManager.lastAccountFlow.flatMapLatest { value ->
                if (value == null) {
                    getAccountsUseCase.observeAccountsDetails().map { it.first() }
                } else {
                    getAccountsUseCase.observeAccountDetailsById(value)
                }
            }.collect {
                _accountDetails.value = it
            }
        }
    }

    fun updateAccountId(selectedAccountId: String) {
        viewModelScope.launch {
            settingsManager.setAccountId(selectedAccountId)
        }
    }

    fun observeSelectedAccount() {

    }
}