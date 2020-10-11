package com.example.holmi_production.money_counter_app.useCases.impl

import com.example.holmi_production.money_counter_app.model.entity.AccountEntity
import com.example.holmi_production.money_counter_app.storage.db.AccountDatabase
import com.example.holmi_production.money_counter_app.storage.db.CategoryDatabase
import com.example.holmi_production.money_counter_app.storage.db.PeriodsDatabase
import com.example.holmi_production.money_counter_app.storage.db.SubCategoryDatabase
import com.example.holmi_production.money_counter_app.useCases.GetAccountsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class GetAccountsUseCaseImpl(
    private val accountDatabase: AccountDatabase,
    private val periodsDatabase: PeriodsDatabase,
    private val categoryDatabase: CategoryDatabase,
    private val subCategoryDatabase: SubCategoryDatabase
) : GetAccountsUseCase {

    override suspend fun getAccounts(): List<AccountEntity> {
        return accountDatabase.getAccounts()
    }

    override suspend fun getAccountById(accountId: String): AccountEntity =
        withContext(Dispatchers.IO) {
            accountDatabase.getAccountById(accountId)
        }

//    override fun observeAccountDetailsById(accountId: String): Flow<DashboardAccountDetails> {
//        return accountDatabase.observeAccountDetailsById(accountId)
//            .combine(periodsDatabase.observePeriod()) { details, period ->
//                details to period
//            }.map { (details, period) ->
//                val transactionDetails =
//                    details.transactions
//                        .filter { it.createdDate >= period.from && it.createdDate <= period.to }
//                        .sortedByDescending { it.createdDate }
//                        .map {
//                            DashboardTransactionDetails(
//                                id = it.id,
//                                createdDate = it.createdDate,
//                                sum = it.sum,
//                                categoryId = if (it.categoryId == null) null else categoryDatabase.getCategoryById(
//                                    it.categoryId
//                                ),
//                                subcategoryId = if (it.subcategoryId == null) null else subCategoryDatabase.getSubcategoryById(
//                                    it.subcategoryId
//                                ),
//                                currencyType = details.account.currencyType
//                            )
//                        }.reversed()
//                DashboardAccountDetails(
//                    account = details.account,
//                    transactions = transactionDetails
//                )
//            }
//    }

    override fun observeAccountById(accountId: String): Flow<AccountEntity> {
        return accountDatabase.observeAccountById(accountId)
    }

    override fun observeAccounts(): Flow<List<AccountEntity>> {
        return accountDatabase.observeAccounts()
    }

//    override fun observeAccountsDetails(): Flow<List<DashboardAccountDetails>> {
//        return accountDatabase.observeAccountsDetails()
//            .combine(periodsDatabase.observePeriod()) { details, period ->
//                details to period
//            }.map { (details, period) ->
//                details.map {account ->
//                    val transaction =
//                        account.transactions
//                            .filter { it.createdDate >= period.from && it.createdDate <= period.to }
//                            .sortedByDescending { it.createdDate }
//                    DashboardAccountDetails(
//                        account = account.account,
//                        transactions = transaction.map {
//                            DashboardTransactionDetails(
//                                id = it.id,
//                                createdDate = it.createdDate,
//                                sum = it.sum,
//                                categoryId = if (it.categoryId == null) null else categoryDatabase.getCategoryById(
//                                    it.categoryId
//                                ),
//                                subcategoryId = if (it.subcategoryId == null) null else subCategoryDatabase.getSubcategoryById(
//                                    it.subcategoryId
//                                ),
//                                currencyType = account.account.currencyType
//                            )
//                        }
//                    )
//                }
//            }
//    }

}