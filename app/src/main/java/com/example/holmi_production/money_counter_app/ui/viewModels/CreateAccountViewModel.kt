package com.example.holmi_production.money_counter_app.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.holmi_production.money_counter_app.model.entity.AccountEntity
import com.example.holmi_production.money_counter_app.model.entity.TransactionEntity
import com.example.holmi_production.money_counter_app.model.enums.AccountType
import com.example.holmi_production.money_counter_app.storage.AppPreference
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
            accountType = AccountType.DEBET
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

}