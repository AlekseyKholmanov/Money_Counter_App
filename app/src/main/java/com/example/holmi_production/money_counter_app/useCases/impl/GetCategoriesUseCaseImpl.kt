package com.example.holmi_production.money_counter_app.useCases.impl

import com.example.holmi_production.money_counter_app.model.CategoryDetails
import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity
import com.example.holmi_production.money_counter_app.storage.db.CategoryDatabase
import com.example.holmi_production.money_counter_app.useCases.GetCategoriesUseCase
import kotlinx.coroutines.flow.Flow

class GetCategoriesUseCaseImpl(
    private val categoriesDatabase: CategoryDatabase
): GetCategoriesUseCase {
    override fun observeCategoriesDetails(): Flow<List<CategoryDetails>> {
        return categoriesDatabase.observeCategoriesDetails()
    }

    override suspend fun getCategories(): List<CategoryEntity> {
        return categoriesDatabase.getCategories()
    }

}