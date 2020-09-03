package com.example.holmi_production.money_counter_app.storage

import com.example.holmi_production.money_counter_app.model.AccountDetails
import com.example.holmi_production.money_counter_app.model.entity.AccountEntity
import kotlinx.coroutines.flow.Flow

interface AccountDatabase {
    suspend fun insert(account: AccountEntity)

    fun observeById(accountId: String): Flow<AccountEntity>

    suspend fun getAccounts(): List<AccountEntity>

    suspend fun getAccountDetailsById(accountId: String): AccountDetails

    suspend fun getAccountsDetails(): List<AccountDetails>

    fun observeAccountDetailsById(accountId: String): Flow<AccountDetails>

    fun observeAccountsDetailsById(): Flow<List<AccountDetails>>

}