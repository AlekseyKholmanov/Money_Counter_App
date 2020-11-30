package com.example.holmi_production.money_counter_app.ui.viewModels

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.Item
import com.example.holmi_production.money_counter_app.model.entity.AccountEntity
import com.example.holmi_production.money_counter_app.model.entity.SubCategoryEntity
import com.example.holmi_production.money_counter_app.model.entity.TransactionEntity
import com.example.holmi_production.money_counter_app.model.enums.CurrencyType
import com.example.holmi_production.money_counter_app.model.toItem
import com.example.holmi_production.money_counter_app.ui.adapter.items.CategoryItem
import com.example.holmi_production.money_counter_app.useCases.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.joda.time.DateTime

class BottomKeyboardViewModel(
    private val getRecentCategoryUseCase: GetRecentCategoryUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val addTransactionUseCase: AddTransactionUseCase,
    private val getAccountUseCase: GetAccountsUseCase
) : ViewModel() {

    private val _categories: MutableLiveData<List<Item>> = MutableLiveData()
    val categories: LiveData<List<Item>> = _categories

    private val _subcategories: MutableLiveData<List<SubCategoryEntity>> = MutableLiveData()
    val subcategories: LiveData<List<SubCategoryEntity>> = _subcategories

    var selectedCategoryId: String? = null

    private var accountInfo: AccountEntity? = null


    fun observeCategories() {
        viewModelScope.launch {
            getCategoriesUseCase.observeCategoriesDetails()
                .map {
                    val newItems = mutableListOf<CategoryItem>()
                    val items =
                        it.mapIndexed { index, categoryDetails -> categoryDetails.toItem(index + 1) }
                    newItems.add(
                        CategoryItem(
                            index = 0,
                            categoryId = null,
                            description = "Добавить",
                            subcategories = listOf(),
                            imageResId = R.drawable.ic_add,
                            color = Color.TRANSPARENT,
                            transitionName = "0item"
                        )
                    )
                    newItems.addAll(items)
                    newItems
                }
                .flowOn(Dispatchers.IO)
                .collect {
                    _categories.value = it
                }
        }
    }

    fun getAccountInfo(accountId: String) {
        viewModelScope.launch {
            accountInfo = getAccountUseCase.getAccountById(accountId)
        }
    }

    fun saveTransaction(accountId: String, sum: Double, comment: String?, subcategoryId: String?) {
        val transaction = TransactionEntity(
            createdDate = DateTime.now(),
            sum = sum,
            comment = comment,
            accountId = accountId,
            subcategoryId = subcategoryId,
            categoryId = selectedCategoryId,
            currencyType = accountInfo?.currencyType ?: CurrencyType.RUBBLE
        )
        viewModelScope.launch {
            addTransactionUseCase.save(transaction)
        }
    }
}