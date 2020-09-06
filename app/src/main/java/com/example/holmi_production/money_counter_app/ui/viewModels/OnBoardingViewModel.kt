package com.example.holmi_production.money_counter_app.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.holmi_production.money_counter_app.model.entity.AccountEntity
import com.example.holmi_production.money_counter_app.model.enums.CurrencyType
import com.example.holmi_production.money_counter_app.model.enums.AccountType
import com.example.holmi_production.money_counter_app.storage.db.AppPreference
import com.example.holmi_production.money_counter_app.useCases.AddAccountUseCase
import kotlinx.coroutines.launch

class OnBoardingViewModel(
    private val createAccountViewModel: AddAccountUseCase,
    private val appPreference: AppPreference
): ViewModel() {

    fun createAccount(
    ) {
        val entity = AccountEntity(
            description = "Main account",
            isHidden = false,
            isCalculatePerDaySum = true,
            password = null,
            accountType = AccountType.DEBET,
            currencyType = CurrencyType.RUBBLE
        )
        viewModelScope.launch {
            appPreference.isOnboardingCompleted = true
            createAccountViewModel.createAccount(entity)
        }
    }

}