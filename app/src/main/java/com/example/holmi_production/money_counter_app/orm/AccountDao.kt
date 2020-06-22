package com.example.holmi_production.money_counter_app.orm

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.holmi_production.money_counter_app.model.AccountDetails
import com.example.holmi_production.money_counter_app.model.entity.AccountEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class AccountDao : BaseDao<AccountEntity>() {

    @Query("SELECT * FROM AccountTable WHERE id=:accountId")
    abstract fun observeAccountById(accountId: String): Flow<AccountEntity>

    @Query("SELECT * FROM AccountTable")
    abstract fun getAccounts(): List<AccountEntity>

    @Transaction
    @Query("SELECT * FROM AccountTable")
    abstract fun getAccountsDetails(): List<AccountDetails>

    @Transaction
    @Query("SELECT * FROM AccountTable WHERE id=:accountId")
    abstract fun getAccountDetailsById(accountId: String): AccountDetails

    @Transaction
    @Query("SELECT * FROM AccountTable WHERE id=:accountId")
    abstract fun observeAccountDetailsById(accountId: String): Flow<AccountDetails>

    @Transaction
    @Query("""SELECT * FROM AccountTable """)
    abstract fun observeAccountsDetails(): Flow<List<AccountDetails>>

}