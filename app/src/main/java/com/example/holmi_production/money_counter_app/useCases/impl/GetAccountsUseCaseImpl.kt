package com.example.holmi_production.money_counter_app.useCases.impl

import com.example.holmi_production.money_counter_app.model.AccountDetails
import com.example.holmi_production.money_counter_app.model.entity.AccountEntity
import com.example.holmi_production.money_counter_app.model.uiModels.DashboardAccountDetails
import com.example.holmi_production.money_counter_app.model.uiModels.DashboardTransactionDetails
import com.example.holmi_production.money_counter_app.storage.db.AccountDatabase
import com.example.holmi_production.money_counter_app.storage.db.CategoryDatabase
import com.example.holmi_production.money_counter_app.storage.db.PeriodsDatabase
import com.example.holmi_production.money_counter_app.storage.db.SubCategoryDatabase
import com.example.holmi_production.money_counter_app.useCases.GetAccountsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class GetAccountsUseCaseImpl(
    private val accountDatabase: AccountDatabase,
    private val periodsDatabase: PeriodsDatabase,
    private val categoryDatabase: CategoryDatabase,
    private val subCategoryDatabase: SubCategoryDatabase
) : GetAccountsUseCase {
    override suspend fun getAccountDetailsById(accountId: String): AccountDetails {
        return accountDatabase.getAccountDetailsById(accountId)
    }

    override suspend fun getAccounts(): List<AccountEntity> {
        return accountDatabase.getAccounts()
    }

    override fun observeAccountDetailsById(accountId: String): Flow<DashboardAccountDetails> {
        return accountDatabase.observeAccountDetailsById(accountId)
            .combine(periodsDatabase.observePeriod()) { details, period ->
                details to period
            }.map { (details, period) ->
                val transactionDetails =
                    details.transactions.filter { it.createdDate >= period.from && it.createdDate <= period.to }
                        .map {
                            DashboardTransactionDetails(
                                id = it.id,
                                createdDate = it.createdDate,
                                sum = it.sum,
                                categoryId = if (it.categoryId == null) null else categoryDatabase.getCategoryById(
                                    it.categoryId
                                ),
                                subcategoryId = if (it.subcategoryId == null) null else subCategoryDatabase.getSubcategoryById(
                                    it.subcategoryId
                                )
                            )
                        }.reversed()
                DashboardAccountDetails(
                    account = details.account,
                    transactions = transactionDetails
                )
            }
    }

    override fun observeAccountsDetails(): Flow<List<DashboardAccountDetails>> {
        return accountDatabase.observeAccountsDetails()
            .combine(periodsDatabase.observePeriod()) { details, period ->
                details to period
            }.map { (details, period) ->
                details.map {
                    val transaction =
                        it.transactions.filter { it.createdDate >= period.from && it.createdDate <= period.to }
                    DashboardAccountDetails(
                        account = it.account,
                        transactions = transaction.map {
                            DashboardTransactionDetails(
                                id = it.id,
                                createdDate = it.createdDate,
                                sum = it.sum,
                                categoryId = if (it.categoryId == null) null else categoryDatabase.getCategoryById(
                                    it.categoryId
                                ),
                                subcategoryId = if (it.subcategoryId == null) null else subCategoryDatabase.getSubcategoryById(
                                    it.subcategoryId
                                )
                            )
                        }
                    )
                }
            }
    }

}