package com.example.holmi_production.money_counter_app.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.holmi_production.money_counter_app.model.CategoryDetails
import com.example.holmi_production.money_counter_app.model.entity.TransactionEntity
import com.example.holmi_production.money_counter_app.useCases.GetRecentCategoryUseCase
import com.example.holmi_production.money_counter_app.useCases.SaveTransactionUseCase
import com.example.holmi_production.money_counter_app.utils.currentTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import org.joda.time.DateTime

class SimpleBottomKeyboardViewModel(
    private val getRecentCategoryUseCase: GetRecentCategoryUseCase,
    private val saveTransactionUseCase: SaveTransactionUseCase
) : ViewModel() {

    private val _category = MutableLiveData<CategoryDetails?>()
    val category: LiveData<CategoryDetails?> = _category

    init {
        viewModelScope.launch {
            getRecentCategoryUseCase.observeResentCategory()
                .flowOn(Dispatchers.IO)
                .collect {
                    _category.value = it
                }
        }
    }

    fun saveTransaction(accountId: String, sum: Double, comment: String?) {
        val transaction = TransactionEntity(
            createdDate = DateTime.now(),
            sum = sum,
            comment = comment,
            accountId = accountId
        )
        viewModelScope.launch {
            saveTransactionUseCase.save(transaction)
        }
    }
}