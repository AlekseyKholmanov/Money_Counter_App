package com.example.holmi_production.money_counter_app.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.holmi_production.money_counter_app.model.dto.TransactionDetailsDTO
import com.example.holmi_production.money_counter_app.ui.adapter.items.CharCategoryItem
import com.example.holmi_production.money_counter_app.useCases.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
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

    var showExpense:Boolean = false

    private val expenses = mutableListOf<CharCategoryItem>()
    private val income = mutableListOf<CharCategoryItem>()


    fun observeTransaction() {
        viewModelScope.launch {
            getTransactionUseCase.observeDetails()
                .map{ transaction ->
                        val divided = transaction.partition { it.transaction.sum > 0 }
                        buildCategoryItem(divided.first) to
                                buildCategoryItem(divided.second)

                }.onEach {
                    expenses.clear()
                    expenses.addAll(it.first)
                    income.clear()
                    income.addAll(it.second)
                }
                .flowOn(Dispatchers.IO)
                .collect {
                    _transactions.value = if(showExpense) it.first else it.second
                }
        }
    }

    private fun buildCategoryItem(transactions: List<TransactionDetailsDTO>): List<CharCategoryItem> {
        val groupped = transactions.groupBy { it.category ?: "No Category" }
        val max = transactions.sumByDouble { it.transaction.sum }
        return groupped.map {
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
        _transactions.value = if(showExpense) expenses else income
    }

    fun updateAccountId(accountId: String) {
        TODO("Not yet implemented")
    }

}