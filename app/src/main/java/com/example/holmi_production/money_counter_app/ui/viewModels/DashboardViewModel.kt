package com.example.holmi_production.money_counter_app.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.holmi_production.money_counter_app.model.AccountDetails
import com.example.holmi_production.money_counter_app.useCases.GetAccountsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
class DashboardViewModel(
    private val getAccountsUseCase: GetAccountsUseCase
): ViewModel(){


    private val _accounts = MutableLiveData<List<AccountDetails>>()
    val accounts: LiveData<List<AccountDetails>> = _accounts

    fun observeAccounts(){
        viewModelScope.launch {
            getAccountsUseCase.observeAccountsDetails()
                .flowOn(Dispatchers.IO)
                .collect {
                    _accounts.value = it
                }
        }
    }
}