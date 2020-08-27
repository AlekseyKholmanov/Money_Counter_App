package com.example.holmi_production.money_counter_app.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.holmi_production.money_counter_app.ui.adapter.items.AccountInfoItem
import com.example.holmi_production.money_counter_app.ui.adapter.items.toInfo
import com.example.holmi_production.money_counter_app.useCases.GetAccountsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
class DashboardViewModel(
    private val getAccountsUseCase: GetAccountsUseCase
) : ViewModel() {


    private val _accounts = MutableLiveData<List<AccountInfoItem>>()
    val accounts: LiveData<List<AccountInfoItem>> = _accounts



    fun observeAccounts() {
        viewModelScope.launch {
            getAccountsUseCase.observeAccountsDetails()
                .map {
                    it.map {
                        it.toInfo()
                    }
                }
                .flowOn(Dispatchers.IO)
                .collect {
                    _accounts.value = it
                }
        }
    }

    fun observeSelectedAccount(){

    }
}