package com.example.holmi_production.money_counter_app.storage.impl

import com.example.holmi_production.money_counter_app.model.AccountDetails
import com.example.holmi_production.money_counter_app.model.entity.AccountEntity
import com.example.holmi_production.money_counter_app.orm.AccountDao
import com.example.holmi_production.money_counter_app.orm.ExpenseDatabase
import com.example.holmi_production.money_counter_app.storage.AccountDatabase
import kotlinx.coroutines.flow.Flow

class AccountDatabaseImpl(
    private val dao: AccountDao
) : AccountDatabase {

    override suspend fun insert(account: AccountEntity) {
        dao.upsert(account)
    }

    override fun observeById(accountId: String): Flow<AccountEntity> {
        return dao.observeAccountById(accountId)
    }

    override suspend fun getAccounts(): List<AccountEntity> {
        return dao.getAccounts()
    }

    override suspend fun getAccountDetailsById(accountId: String): AccountDetails {
        return dao.getAccountDetailsById(accountId)
    }

    override suspend fun getAccountsDetails(): List<AccountDetails> {
        return dao.getAccountsDetails()
    }

    override fun observeAccountDetailsById(accountId: String): Flow<AccountDetails> {
        return dao.observeAccountDetailsById(accountId)
    }

    override fun observeAccountsDetailsById(): Flow<List<AccountDetails>> {
        return dao.observeAccountsDetails()
    }

}