package com.example.holmi_production.money_counter_app.useCases.impl

import com.example.holmi_production.money_counter_app.model.CategoryDetails
import com.example.holmi_production.money_counter_app.storage.CategoryDatabase
import com.example.holmi_production.money_counter_app.storage.RecentCategoryDatabase
import com.example.holmi_production.money_counter_app.useCases.GetRecentCategoryUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetRecentCategoryUseCaseImpl(
    private val recentDb: RecentCategoryDatabase,
    private val categoriesDb: CategoryDatabase
) : GetRecentCategoryUseCase {
    override fun observeResentCategory(): Flow<CategoryDetails?> {
        return recentDb.observeRecentCategory()
            .map {
                if (it == null) {
                    null
                } else {
                    categoriesDb.getCategoryDetailsById(it)
                }
            }
    }
}