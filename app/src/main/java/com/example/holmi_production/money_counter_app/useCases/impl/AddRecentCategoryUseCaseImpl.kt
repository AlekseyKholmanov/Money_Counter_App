package com.example.holmi_production.money_counter_app.useCases.impl

import com.example.holmi_production.money_counter_app.storage.db.RecentCategoryDatabase
import com.example.holmi_production.money_counter_app.useCases.AddRecentCategoryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddRecentCategoryUseCaseImpl(
    private val recentCategoryDatabase: RecentCategoryDatabase
) : AddRecentCategoryUseCase {

    override suspend fun setRecentCategory(categoryId: String) = withContext(Dispatchers.IO){
        recentCategoryDatabase.updateTimestamp(categoryId)
    }

}