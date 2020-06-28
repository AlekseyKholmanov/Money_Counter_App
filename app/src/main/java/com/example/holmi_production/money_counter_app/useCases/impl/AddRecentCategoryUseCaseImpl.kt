package com.example.holmi_production.money_counter_app.useCases.impl

import com.example.holmi_production.money_counter_app.storage.RecentCategoryDatabase
import com.example.holmi_production.money_counter_app.useCases.AddRecentCategoryUseCase

class AddRecentCategoryUseCaseImpl(
    private val recentCategoryDatabase: RecentCategoryDatabase
) : AddRecentCategoryUseCase {

    override suspend fun setRecentCategory(categoryId: String) {
        recentCategoryDatabase.updateTimestamp(categoryId)
    }

}