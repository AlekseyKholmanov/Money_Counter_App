package com.example.holmi_production.money_counter_app.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.holmi_production.money_counter_app.model.uiModels.AccountInfo
import com.example.holmi_production.money_counter_app.model.uiModels.toInfo
import com.example.holmi_production.money_counter_app.useCases.GetAccountsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
class DashboardViewModel(
    private val getAccountsUseCase: GetAccountsUseCase
) : ViewModel() {


    private val _accounts = MutableLiveData<List<AccountInfo>>()
    val accounts: LiveData<List<AccountInfo>> = _accounts

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

    fun setRecentAccount(accountId: Int) {
        val account = accounts.value!![accountId].id
    }
}