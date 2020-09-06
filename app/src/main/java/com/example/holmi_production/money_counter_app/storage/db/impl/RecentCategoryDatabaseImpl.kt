package com.example.holmi_production.money_counter_app.storage.db.impl

import com.example.holmi_production.money_counter_app.model.entity.RecentCategoryEntity
import com.example.holmi_production.money_counter_app.orm.RecentCategoryDao
import com.example.holmi_production.money_counter_app.storage.db.RecentCategoryDatabase
import kotlinx.coroutines.flow.Flow

class RecentCategoryDatabaseImpl(
    private val dao: RecentCategoryDao
) : RecentCategoryDatabase {

    override suspend fun updateTimestamp(categoryId: String) {
        dao.upsert(RecentCategoryEntity(categoryId))
    }

    override fun observeRecentCategory(): Flow<String?> {
        return dao.observeRecentCategory()
    }

}