package com.example.holmi_production.money_counter_app.ui.viewModels

import android.util.Log
import androidx.lifecycle.*
import com.example.holmi_production.money_counter_app.model.dto.TransactionDetailsDTO
import com.example.holmi_production.money_counter_app.model.entity.AccountEntity
import com.example.holmi_production.money_counter_app.ui.adapter.items.CharCategoryItem
import com.example.holmi_production.money_counter_app.useCases.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.abs

class ChartViewModel(
    private val getTransactionUseCase: GetTransactionUseCase,
    private val getAccountsUseCase: GetAccountsUseCase,
    private val editTransactionUSeCase: EditTransactionUseCase,
    private val getActivePeriodUseCase: GetActivePeriodUseCase,
    private val updateActivePeriodUseCase: UpdateActivePeriodUseCase
) : ViewModel() {

    private val _transactions = MutableLiveData<List<CharCategoryItem>>()
    val transactions: LiveData<List<CharCategoryItem>> = _transactions

    private val _accountId = MutableLiveData<String?>(null)
    val accountId: LiveData<String?> = _accountId

    private val _accountEntity = MutableLiveData<AccountEntity?>(null)
    val accountEntity: LiveData<AccountEntity?> = _accountEntity


    var showExpense: Boolean = false

    private val expenses = mutableListOf<CharCategoryItem>()
    private val income = mutableListOf<CharCategoryItem>()


    fun observeTransaction() {
        viewModelScope.launch {
            val accountFlow = accountId.asFlow().map {
                if(it.isNullOrEmpty())null else getAccountsUseCase.getAccountById(it)
            }
            getTransactionUseCase.observeDetails()
                .combine(accountFlow) { transactions, accountId -> (if (accountId == null) transactions else transactions.filter { it.transaction.accountId == accountId.id }) to accountId }
                .map { (transaction, accountId) ->
                    val divided = transaction.partition { it.transaction.sum > 0 }
                    buildCategoryItem(divided.first) to
                            buildCategoryItem(divided.second) to accountId

                }.onEach { (transactions, accountId) ->
                    expenses.clear()
                    expenses.addAll(transactions.first)
                    income.clear()
                    income.addAll(transactions.second)
                    Log.d("M_M_M", "$accountId")
                    withContext(Dispatchers.Main){_accountEntity.value = accountId}
                }
                .flowOn(Dispatchers.IO)
                .collect { (transactions, qwe) ->

                    Log.d("M_M_M", "collect")
                    _transactions.value =
                        if (showExpense) transactions.first else transactions.second
                }
        }
    }

    private fun buildCategoryItem(transactions: List<TransactionDetailsDTO>): List<CharCategoryItem> {
        val grouped = transactions.groupBy { it.category ?: "No Category" }
        val max = transactions.sumByDouble { it.transaction.sum }
        return grouped.map {
            val sum = it.value.sumByDouble { it.transaction.sum }
            val category = it.value.first().category
            CharCategoryItem(
                categoryName = category?.description ?: "No Category",
                sum = sum,
                percentage = sum / max * 100,
                color = category?.color,
                categoryImage = category?.imageId
            )
        }.sortedByDescending { abs(it.sum) }
    }

    fun swapValues() {
        showExpense = !showExpense
        _transactions.value = if (showExpense) expenses else income
    }

    fun updateAccountId(accountId: String) {
        _accountId.value = if (accountId.isEmpty()) null else accountId
    }

}