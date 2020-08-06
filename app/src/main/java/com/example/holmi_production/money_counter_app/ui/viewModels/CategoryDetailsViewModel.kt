package com.example.holmi_production.money_counter_app.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.holmi_production.money_counter_app.model.CategoryDetails
import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity
import com.example.holmi_production.money_counter_app.model.entity.SubCategoryEntity
import com.example.holmi_production.money_counter_app.useCases.AddCategoryUseCase
import com.example.holmi_production.money_counter_app.useCases.GetCategoryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.util.*

class CategoryDetailsViewModel(
    private val addCategoryUseCase: AddCategoryUseCase,
    private val addSub: SubCategoryEntity
    private val getCategoryUseCase: GetCategoryUseCase
) : ViewModel() {

    private val _categories: MutableLiveData<CategoryDetails?> = MutableLiveData()
    val categories: LiveData<CategoryDetails?> = _categories
    var categoryId = ""

    fun createCategory(
        imageId: Int,
        color: Int,
        description: String,
        subcategories: List<SubCategoryEntity>
    ) {
        viewModelScope.launch {
            val category = CategoryEntity(
                id = categoryId,
                imageId = imageId,
                color = color,
                description = description
            )

            addCategoryUseCase.insert(category)
        }
    }

    fun observeCategoryById(categoryId: String?) {
        this.categoryId = categoryId ?: UUID.randomUUID().toString()
        viewModelScope.launch {
            getCategoryUseCase.observeCategoryDetailsById(categoryId)
                .flowOn(Dispatchers.IO)
                .collect {
                    _categories.value = it
                }
        }
    }

}