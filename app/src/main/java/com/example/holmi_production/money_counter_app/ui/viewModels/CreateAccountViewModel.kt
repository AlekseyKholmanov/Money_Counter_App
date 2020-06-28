package com.example.holmi_production.money_counter_app.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.holmi_production.money_counter_app.model.entity.AccountEntity
import com.example.holmi_production.money_counter_app.model.enums.AccountType
import com.example.holmi_production.money_counter_app.storage.AppPreference
import com.example.holmi_production.money_counter_app.useCases.AddAccountUseCase
import kotlinx.coroutines.launch

class CreateAccountViewModel(
    private val createAccountViewModel: AddAccountUseCase,
    private val appPreference: AppPreference
) : ViewModel() {

    fun createAccount(
        description: String,
        isHidden: Boolean,
        isCalculatePerDay: Boolean,
        password: String?
    ) {
        val entity = AccountEntity(
            description = description,
            isHidden = isHidden,
            isCalculatePerDaySum = isCalculatePerDay,
            password = password,
            accountType = AccountType.DEBET
        )
        viewModelScope.launch {
            createAccountViewModel.createAccount(entity)
        }
    }

}