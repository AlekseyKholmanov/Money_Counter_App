package com.example.holmi_production.money_counter_app.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.holmi_production.money_counter_app.model.CategoryDetails
import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity
import com.example.holmi_production.money_counter_app.model.entity.SubCategoryEntity
import com.example.holmi_production.money_counter_app.useCases.AddCategoryUseCase
import com.example.holmi_production.money_counter_app.useCases.AddSubcategoryUseCase
import com.example.holmi_production.money_counter_app.useCases.EditSubcategoryUseCase
import com.example.holmi_production.money_counter_app.useCases.GetCategoryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.*

class CategoryDetailsViewModel(
    private val addCategoryUseCase: AddCategoryUseCase,
    private val getCategoryUseCase: GetCategoryUseCase,
    private val addSubcategoryUseCase: AddSubcategoryUseCase,
    private val editSubcategoryUseCase: EditSubcategoryUseCase
) : ViewModel() {

    private val _categories: MutableLiveData<CategoryEntity?> = MutableLiveData()
    val categories: LiveData<CategoryEntity?> = _categories

    private val _subcategories = MutableLiveData<List<SubCategoryEntity>>()
    val subcategories: LiveData<List<SubCategoryEntity>> = _subcategories
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
        viewModelScope.launch {
            addSubcategoryUseCase.insertAll(subcategories)
        }
    }

    fun deleteSubcategory(subcategoryId:String){
        viewModelScope.launch {
            editSubcategoryUseCase.delete(subcategoryId)
        }
    }

    fun observeCategoryById(categoryId: String?) {
        this.categoryId = categoryId ?: UUID.randomUUID().toString()
        viewModelScope.launch {
            getCategoryUseCase.observeCategoryDetailsById(categoryId)
                .map { it?.category to it?.subcategory?.filter { !it.isDeleted } }
                .flowOn(Dispatchers.IO)
                .collect {(category, subcategory) ->
                    _categories.value = category
                    _subcategories.value = subcategory ?: listOf()
                }
        }
    }

}