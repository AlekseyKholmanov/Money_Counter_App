package com.example.holmi_production.money_counter_app.storage.db

import com.example.holmi_production.money_counter_app.model.AccountDetails
import com.example.holmi_production.money_counter_app.model.entity.AccountEntity
import kotlinx.coroutines.flow.Flow

interface AccountDatabase {
    suspend fun insert(account: AccountEntity)

    fun observeById(accountId: String): Flow<AccountEntity>

    suspend fun getAccountById(accountId:String): AccountEntity

    suspend fun getAccounts(): List<AccountEntity>

    suspend fun getAccountsDetails(): List<AccountDetails>

    fun observeAccountsDetails(): Flow<List<AccountDetails>>

    fun observeAccountById(accountId: String): Flow<AccountEntity>

    fun observeAccounts(): Flow<List<AccountEntity>>



}