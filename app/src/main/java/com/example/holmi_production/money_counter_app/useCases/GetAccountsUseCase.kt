package com.example.holmi_production.money_counter_app.useCases

import android.accounts.Account
import com.example.holmi_production.money_counter_app.model.AccountDetails
import com.example.holmi_production.money_counter_app.model.entity.AccountEntity
import com.example.holmi_production.money_counter_app.model.uiModels.DashboardAccountDetails
import kotlinx.coroutines.flow.Flow

interface GetAccountsUseCase {

    suspend fun getAccountDetailsById(accountId: String): AccountDetails

    suspend fun getAccounts(): List<AccountEntity>

    fun observeAccountDetailsById(accountId: String): Flow<DashboardAccountDetails>

    fun observeAccountsDetails(): Flow<List<DashboardAccountDetails>>
}