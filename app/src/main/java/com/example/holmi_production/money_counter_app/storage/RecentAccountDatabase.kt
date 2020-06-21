package com.example.holmi_production.money_counter_app.storage

import kotlinx.coroutines.flow.Flow

interface RecentAccountDatabase {

    suspend fun updateTimestamp(accountId: String)

    fun observeRecentAccount(): Flow<String?>

}