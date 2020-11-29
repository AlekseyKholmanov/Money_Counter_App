package com.example.holmi_production.money_counter_app.ui.viewModels

import android.util.Log
import androidx.lifecycle.*
import com.example.holmi_production.money_counter_app.model.DashbordFilter
import com.example.holmi_production.money_counter_app.model.TransactionDetails
import com.example.holmi_production.money_counter_app.model.dto.CurrencyCourseDTO
import com.example.holmi_production.money_counter_app.model.dto.TransactionDetailsDTO
import com.example.holmi_production.money_counter_app.model.entity.AccountEntity
import com.example.holmi_production.money_counter_app.model.enums.CurrencyType
import com.example.holmi_production.money_counter_app.storage.data_store.LastAccountManager
import com.example.holmi_production.money_counter_app.ui.adapter.items.AccountSummary
import com.example.holmi_production.money_counter_app.ui.adapter.items.TransactionGroupItem
import com.example.holmi_production.money_counter_app.ui.adapter.items.toDashboardItem
import com.example.holmi_production.money_counter_app.useCases.GetAccountsUseCase
import com.example.holmi_production.money_counter_app.useCases.GetCurrenciesCourseUseCase
import com.example.holmi_production.money_counter_app.useCases.GetTransactionUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
class DashboardViewModel(
    private val getAccountsUseCase: GetAccountsUseCase,
    private val getTransactionUseCase: GetTransactionUseCase,
    private val getCurrenciesCourseUseCase: GetCurrenciesCourseUseCase,
    private val lastAccountManager: LastAccountManager
) : ViewModel() {

    private val _accountDetails = MutableLiveData<List<TransactionGroupItem>>()
    val accountDetails: LiveData<List<TransactionGroupItem>> = _accountDetails

    private val _currency = MutableLiveData<CurrencyType?>(null)
    val currency: LiveData<CurrencyType?> = _currency

    private val _filerValue = MutableLiveData(DashbordFilter.ALL)
    val filterValue: LiveData<DashbordFilter> = _filerValue

    private val _accountSummary = MutableLiveData<AccountSummary>()
    val accountSummary: LiveData<AccountSummary> = _accountSummary

    var course: CurrencyCourseDTO? = null
    var accountDetailsEntity: AccountEntity? = null

    init {
        getCurrenciesCourseUseCase.observeCourses()
            .flowOn(Dispatchers.IO)
            .onEach {
                if (it.isNotEmpty())
                    course = it.last()
            }.launchIn(viewModelScope)

        lastAccountManager.lastAccountFlow
            .map {
                return@map if (it == null) getAccountsUseCase.getAccounts().last() else
                    getAccountsUseCase.getAccountById(it)
            }.flowOn(Dispatchers.IO)
            .onEach { accountEntity ->
                accountEntity.let {
                    _currency.value = it.currencyType
                }
            }.launchIn(viewModelScope)

        val lastAccountManagerFlow = lastAccountManager.lastAccountFlow
            .flatMapLatest { value ->
                if (value == null) {
                    getAccountsUseCase.observeAccounts().map { it.first() }
                } else {
                    getAccountsUseCase.observeAccountById(value)
                }
                    .onEach {
                        accountDetailsEntity = it
                    }
                    .flatMapLatest { account ->
                        getTransactionUseCase.observeDetailsByAccountId(account.id)
                    }
            }
            combine(lastAccountManagerFlow, currency.asFlow(), filterValue.asFlow())        {
                transaction, selectedCurrency, filters -> filterTransaction(transaction, filters) to selectedCurrency
            }.map { (transactions, selectedCurrency) ->
                val divided = transactions.partition { it.transaction.sum > 0 }
                val accountSummary = AccountSummary(
                    accountId = accountDetailsEntity!!.id,
                    description = accountDetailsEntity!!.description,
                    balance = transactions.sumByDouble { it.transaction.sum },
                    income = divided.first.sumByDouble { it.transaction.sum },
                    expenses = divided.second.sumByDouble { it.transaction.sum },
                    currencyType = accountDetailsEntity!!.currencyType
                )
                Log.d("M_DashboardViewModel","selectedCurrensy in map:$selectedCurrency accountType:${accountDetailsEntity!!.currencyType}")
                val transactionInfo =
                    if (selectedCurrency == null || selectedCurrency == accountDetailsEntity!!.currencyType) {
                        buildTransactionItems(transactions)
                    } else {
                        buildTransactionItems(transactions, changeCurrency = true)
                    }
                accountSummary to transactionInfo
            }
            .flowOn(Dispatchers.IO)
            .onEach { (summary, transactions) ->
                _accountDetails.value = transactions
                _accountSummary.value = summary
            }
            .launchIn(viewModelScope)
    }

    fun updateAccountId(selectedAccountId: String) {
        viewModelScope.launch {
            lastAccountManager.setAccountId(selectedAccountId)
        }
    }

    fun updateDisplayedCurrency(selected: CurrencyType) {
        Log.d("M_DashboardViewModel", "change currency $selected")
        _currency.value = selected
    }

    fun filterTransaction(transactions: List<TransactionDetailsDTO>, filter: DashbordFilter): List<TransactionDetailsDTO>{
        return when (filter) {
            DashbordFilter.ALL -> transactions
            DashbordFilter.INCOME -> transactions.filter { it.transaction.sum > 0 }
            else -> {
                transactions.filter { it.transaction.sum <0 }
            }
        }
    }

    private fun buildTransactionItems(
        items: List<TransactionDetailsDTO>,
        changeCurrency: Boolean = false
    ): List<TransactionGroupItem> {
        val adapterItems = if (changeCurrency) {
            items.map {
                if (it.transaction.currencyType == currency.value) {
                    it.toDashboardItem()
                } else {
                    val convertedSum = (it.transaction.sum.toFloat() / (course?.courses?.get(it.transaction.currencyType)
                        ?: 1f) * (course?.courses?.get(currency.value)
                        ?: 1f)).toDouble()
                    it.toDashboardItem(convertedSum, currency.value)
                }
            }
        } else items.map { it.toDashboardItem() }
        return adapterItems.sortedByDescending { it.createdDate.withTimeAtStartOfDay() }
            .groupBy { it.createdDate.withTimeAtStartOfDay() }
            .map {
                TransactionGroupItem(
                    it.key,
                    it.value.sortedByDescending { it.createdDate })
            }
    }

    fun updateFilter(filter: DashbordFilter) {
        _filerValue.value = filter
    }
}