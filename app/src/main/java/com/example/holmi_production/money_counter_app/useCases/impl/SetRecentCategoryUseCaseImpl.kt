package com.example.holmi_production.money_counter_app.useCases.impl

import com.example.holmi_production.money_counter_app.storage.RecentCategoryDatabase
import com.example.holmi_production.money_counter_app.useCases.SetRecentCategoryUseCase

class SetRecentCategoryUseCaseImpl(
    private val recentCategoryDatabase: RecentCategoryDatabase
) : SetRecentCategoryUseCase {

    override suspend fun setRecentCategory(categoryId: String) {
        recentCategoryDatabase.updateTimestamp(categoryId)
    }

}