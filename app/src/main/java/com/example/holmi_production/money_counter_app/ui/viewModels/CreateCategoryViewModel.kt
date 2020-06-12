package com.example.holmi_production.money_counter_app.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.holmi_production.money_counter_app.interactor.CategoryInteractor
import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity
import kotlinx.coroutines.launch

class CreateCategoryViewModel(
    private val categoryInteractor: CategoryInteractor
) : ViewModel() {

    fun createCategory(categoryEntity: CategoryEntity) {
        viewModelScope.launch {
            categoryInteractor.insert(categoryEntity)
        }
    }
}