package com.example.holmi_production.money_counter_app.ui.viewModels

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.enums.Images
import com.example.holmi_production.money_counter_app.model.Item
import com.example.holmi_production.money_counter_app.model.entity.SubCategoryEntity
import com.example.holmi_production.money_counter_app.model.entity.TransactionEntity
import com.example.holmi_production.money_counter_app.model.toItem
import com.example.holmi_production.money_counter_app.ui.adapter.items.CategoryItem
import com.example.holmi_production.money_counter_app.useCases.AddTransactionUseCase
import com.example.holmi_production.money_counter_app.useCases.GetCategoriesUseCase
import com.example.holmi_production.money_counter_app.useCases.GetRecentCategoryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.joda.time.DateTime

class BottomKeyboardViewModel(
    private val getRecentCategoryUseCase: GetRecentCategoryUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val addTransactionUseCase: AddTransactionUseCase
) : ViewModel() {

    private val _categories: MutableLiveData<List<Item>> = MutableLiveData()
    val categories: LiveData<List<Item>> = _categories

    private val _subcategories: MutableLiveData<List<SubCategoryEntity>> = MutableLiveData()
    val subcategories: LiveData<List<SubCategoryEntity>> = _subcategories

    var selectedCategoryId:String? = null


    fun observeCategories() {
        viewModelScope.launch {
            getCategoriesUseCase.observeCategoriesDetails()
                .map {
                    val newItems = mutableListOf<CategoryItem>()
                    val items = it.mapIndexed { index, categoryDetails ->   categoryDetails.toItem(index + 1) }
                    newItems.add( CategoryItem(index = 0, categoryId = null, description = "Добавить", subcategories = listOf(), imageResId = R.drawable.ic_add, color = Color.TRANSPARENT))
                    newItems.addAll(items)
                    newItems
                }
                .flowOn(Dispatchers.IO)
                .collect {
                    _categories.value = it
                }
        }
    }

    fun saveTransaction(accountId: String, sum: Double, comment: String?, subcategoryId: String?) {
        val transaction = TransactionEntity(
            createdDate = DateTime.now(),
            sum = sum,
            comment = comment,
            accountId = accountId,
            subcategoryId = subcategoryId,
            categoryId = selectedCategoryId
        )
        viewModelScope.launch {
            addTransactionUseCase.save(transaction)
        }
    }
}