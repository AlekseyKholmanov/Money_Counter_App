package com.example.holmi_production.money_counter_app.useCases.impl

import com.example.holmi_production.money_counter_app.model.AccountDetails
import com.example.holmi_production.money_counter_app.storage.AccountDatabase
import com.example.holmi_production.money_counter_app.useCases.GetAccountsUseCase
import kotlinx.coroutines.flow.Flow

class GetAccountsUseCaseImpl(
    private val accountDatabase: AccountDatabase
): GetAccountsUseCase {
    override suspend fun getAccountDetailsById(accountId: String): AccountDetails {
        return accountDatabase.getAccountDetailsById(accountId)
    }

    override fun observeAccountDetailsById(accountId: String): Flow<AccountDetails> {
        return accountDatabase.observeAccountDetailsById(accountId)
    }

    override fun observeAccountsDetails(): Flow<List<AccountDetails>> {
        return accountDatabase.observeAccountsDetailsById()
    }

}