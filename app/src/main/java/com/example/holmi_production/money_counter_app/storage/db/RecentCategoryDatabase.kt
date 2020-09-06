package com.example.holmi_production.money_counter_app.storage.db

import kotlinx.coroutines.flow.Flow

interface RecentCategoryDatabase {

    suspend fun updateTimestamp(categoryId: String)

    fun observeRecentCategory(): Flow<String?>
}