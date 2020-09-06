package com.example.holmi_production.money_counter_app.storage.db.impl

import com.example.holmi_production.money_counter_app.model.AccountDetails
import com.example.holmi_production.money_counter_app.model.entity.AccountEntity
import com.example.holmi_production.money_counter_app.orm.AccountDao
import com.example.holmi_production.money_counter_app.storage.db.AccountDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class AccountDatabaseImpl(
    private val dao: AccountDao
) : AccountDatabase {

    override suspend fun insert(account: AccountEntity) = withContext(Dispatchers.IO) {
        dao.upsert(account)
    }

    override fun observeById(accountId: String): Flow<AccountEntity> {
        return dao.observeAccountById(accountId)
    }

    override suspend fun getAccounts(): List<AccountEntity> = withContext(Dispatchers.IO) {
        dao.getAccounts()
    }


    override suspend fun getAccountDetailsById(accountId: String): AccountDetails = withContext(Dispatchers.IO) {
        dao.getAccountDetailsById(accountId)
    }

    override suspend fun getAccountsDetails(): List<AccountDetails> = withContext(Dispatchers.IO) {
        dao.getAccountsDetails()
    }

    override fun observeAccountDetailsById(accountId: String): Flow<AccountDetails> {
        return dao.observeAccountDetailsById(accountId)
    }

    override fun observeAccountsDetails(): Flow<List<AccountDetails>> {
        return dao.observeAccountsDetails()
    }

}