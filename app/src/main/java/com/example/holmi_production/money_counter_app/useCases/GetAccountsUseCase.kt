package com.example.holmi_production.money_counter_app.useCases

import android.accounts.Account
import com.example.holmi_production.money_counter_app.model.AccountDetails
import com.example.holmi_production.money_counter_app.model.entity.AccountEntity
import com.example.holmi_production.money_counter_app.model.uiModels.DashboardAccountDetails
import kotlinx.coroutines.flow.Flow

interface GetAccountsUseCase {

    suspend fun getAccounts(): List<AccountEntity>

    suspend fun getAccountById(accountId: String): AccountEntity

    fun observeAccountById(accountId: String): Flow<AccountEntity>

    fun observeAccounts():Flow<List<AccountEntity>>
}