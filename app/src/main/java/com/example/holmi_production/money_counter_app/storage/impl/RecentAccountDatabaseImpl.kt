package com.example.holmi_production.money_counter_app.storage.impl

import com.example.holmi_production.money_counter_app.model.entity.RecentAccountEntity
import com.example.holmi_production.money_counter_app.orm.ExpenseDatabase
import com.example.holmi_production.money_counter_app.orm.RecentAccountDao
import com.example.holmi_production.money_counter_app.storage.RecentAccountDatabase
import kotlinx.coroutines.flow.Flow

class RecentAccountDatabaseImpl(
    private val dao: RecentAccountDao
): RecentAccountDatabase {

    override suspend fun updateTimestamp(accountId: String) {
        val entity = RecentAccountEntity(accountId = accountId)
        dao.upsert(entity)
    }

    override fun observeRecentAccount(): Flow<String?> {
        return dao.observeRecentAccount()
    }

}