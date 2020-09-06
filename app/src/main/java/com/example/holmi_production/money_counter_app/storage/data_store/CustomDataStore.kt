package com.example.holmi_production.money_counter_app.storage.data_store

import android.content.Context
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class SettingManager(context: Context) {

    private val dataStore = context.createDataStore("pref_data_store")

    companion object {
        val LAST_ACCOUNT_ID = preferencesKey<String>("last_account_id")
    }

    suspend fun setAccountId(accountId: String) {
        dataStore.edit { preferences -> preferences[LAST_ACCOUNT_ID] = accountId }
    }

    val lastAccountFlow: Flow<String?> = dataStore.data.map { preferences ->
        preferences[LAST_ACCOUNT_ID]
    }


}