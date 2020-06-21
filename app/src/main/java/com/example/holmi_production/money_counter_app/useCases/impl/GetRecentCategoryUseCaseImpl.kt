package com.example.holmi_production.money_counter_app.useCases.impl

import com.example.holmi_production.money_counter_app.model.CategoryDetails
import com.example.holmi_production.money_counter_app.storage.CategoryDatabase
import com.example.holmi_production.money_counter_app.storage.RecentCategoryDatabase
import com.example.holmi_production.money_counter_app.useCases.GetRecentCategoryUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetRecentCategoryUseCaseImpl(
    private val recentDatabase: RecentCategoryDatabase,
    private val categoriesDatabase: CategoryDatabase
) : GetRecentCategoryUseCase {

    override fun observeResentCategory(): Flow<CategoryDetails?> {
        return recentDatabase.observeRecentCategory()
            .map {
                if (it == null) {
                    null
                } else {
                    categoriesDatabase.getCategoryDetailsById(it)
                }
            }
    }
}