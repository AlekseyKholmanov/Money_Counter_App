package com.example.holmi_production.money_counter_app.useCases

import com.example.holmi_production.money_counter_app.model.AccountDetails
import kotlinx.coroutines.flow.Flow

interface GetAccountsUseCase {

    suspend fun getAccountDetailsById(accountId: String): AccountDetails

    fun observeAccountDetailsById(accountId: String): Flow<AccountDetails>

    fun observeAccountsDetails(): Flow<List<AccountDetails>>
}