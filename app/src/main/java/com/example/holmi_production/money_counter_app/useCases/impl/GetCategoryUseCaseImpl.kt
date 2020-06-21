package com.example.holmi_production.money_counter_app.useCases.impl

import com.example.holmi_production.money_counter_app.model.CategoryDetails
import com.example.holmi_production.money_counter_app.storage.CategoryDatabase
import com.example.holmi_production.money_counter_app.useCases.GetCategoryUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class GetCategoryUseCaseImpl(
    private val categoryDatabase: CategoryDatabase
) : GetCategoryUseCase {

    override fun observeCategoryDetailsById(categoryId: String?): Flow<CategoryDetails?> {
        return if (categoryId == null) flowOf(null) else {
            categoryDatabase.observeCategoryDetailsById(categoryId)
        }
    }


    override suspend fun getCategoryDetailsById(categoryId: String): CategoryDetails {
        return categoryDatabase.getCategoryDetailsById(categoryId)
    }

}