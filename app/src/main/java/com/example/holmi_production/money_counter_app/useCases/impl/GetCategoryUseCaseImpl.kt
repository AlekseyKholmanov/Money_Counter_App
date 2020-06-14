package com.example.holmi_production.money_counter_app.useCases.impl

import com.example.holmi_production.money_counter_app.model.CategoryDetails
import com.example.holmi_production.money_counter_app.storage.CategoryDatabase
import com.example.holmi_production.money_counter_app.useCases.GetCategoryUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class GetCategoryUseCaseImpl(
    private val categoryDb: CategoryDatabase
) : GetCategoryUseCase {

    override fun observeCategoryById(categoryId: String?): Flow<CategoryDetails?> {
        return if (categoryId == null) flowOf(null) else {
            categoryDb.observeCategoryDetailsById(categoryId)
        }
    }

}