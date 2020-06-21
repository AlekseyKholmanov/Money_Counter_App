package com.example.holmi_production.money_counter_app.orm

import androidx.room.Dao
import androidx.room.Query
import com.example.holmi_production.money_counter_app.model.entity.RecentAccountEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class RecentAccountDao : BaseDao<RecentAccountEntity>() {

    @Query(
        """SELECT RecentAccount.accountId FROM RecentAccount
        ORDER BY timestamp DESC LIMIT 1
    """
    )
    abstract fun observeRecentAccount(): Flow<String?>
}