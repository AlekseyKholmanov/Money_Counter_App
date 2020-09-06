package com.example.holmi_production.money_counter_app.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.holmi_production.money_counter_app.model.entity.AccountEntity
import com.example.holmi_production.money_counter_app.model.entity.TransactionEntity
import com.example.holmi_production.money_counter_app.model.enums.CurrencyType
import com.example.holmi_production.money_counter_app.model.enums.AccountType
import com.example.holmi_production.money_counter_app.storage.db.AppPreference
import com.example.holmi_production.money_counter_app.useCases.AddAccountUseCase
import com.example.holmi_production.money_counter_app.useCases.AddTransactionUseCase
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import java.util.*

class CreateAccountViewModel(
    private val createAccountViewModel: AddAccountUseCase,
    private val addTransactionUseCase: AddTransactionUseCase,
    private val appPreference: AppPreference
) : ViewModel() {

    private val _currencyType: MutableLiveData<CurrencyType?> = MutableLiveData()
    val currencyType: LiveData<CurrencyType?> = _currencyType

    fun createAccount(
        description: String,
        isHidden: Boolean,
        isCalculatePerDay: Boolean,
        password: String?,
        startBalance: Double
    ) {
        val accountId = UUID.randomUUID().toString()
        val entity = AccountEntity(
            id = accountId,
            description = description,
            isHidden = isHidden,
            isCalculatePerDaySum = isCalculatePerDay,
            password = password,
            accountType = AccountType.DEBET,
            currencyType = currencyType.value ?: CurrencyType.RUBBLE
        )
        val transaction = TransactionEntity(
            createdDate = DateTime.now(),
            sum = startBalance,
            accountId = accountId
        )
        viewModelScope.launch {
            appPreference.isOnboardingCompleted = true
            createAccountViewModel.createAccount(entity)
            addTransactionUseCase.save(transaction)
        }
    }

    fun updateCurrency(currencyType: CurrencyType) {
        _currencyType.value = currencyType
    }

}