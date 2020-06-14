package com.example.holmi_production.money_counter_app.orm

import androidx.room.Dao
import androidx.room.Query
import com.example.holmi_production.money_counter_app.model.entity.RecentCategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class RecentCategoryDao : BaseDao<RecentCategoryEntity>() {

    @Query(
        """SELECT RecentCategory.categoryId FROM RecentCategory 
        INNER JOIN CategoryTable ON CategoryTable.id = RecentCategory.categoryId
        WHERE isDeleted = 0
        ORDER BY timestamp DESC LIMIT 1
    """)
    abstract fun observeRecentCategory(): Flow<String?>
}