package com.example.holmi_production.money_counter_app.storage.impl

import com.example.holmi_production.money_counter_app.model.entity.RecentCategoryEntity
import com.example.holmi_production.money_counter_app.orm.ExpenseDatabase
import com.example.holmi_production.money_counter_app.storage.RecentCategoryDatabase
import kotlinx.coroutines.flow.Flow

class RecentCategoryDatabaseImpl(
    val database: ExpenseDatabase
) : RecentCategoryDatabase {

    val dao = database.recentCategoryDao()

    override suspend fun updateTimestamp(categoryId: String) {
        dao.upsert(RecentCategoryEntity(categoryId))
    }

    override fun observeRecentCategory(): Flow<String?> {
        return dao.observeRecentCategory()
    }
}