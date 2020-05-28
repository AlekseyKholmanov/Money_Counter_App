package com.example.holmi_production.money_counter_app.storage

import com.example.holmi_production.money_counter_app.model.entity.SpendingDetails
import com.example.holmi_production.money_counter_app.model.entity.SpendingEntity
import kotlinx.coroutines.flow.Flow

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
interface SpendingDatabase {

    suspend fun insert(spending: SpendingEntity)

    fun observeSpendings(): Flow<List<SpendingEntity>>

    suspend fun getSpendings(): List<SpendingEntity>

    suspend fun delete(spending: SpendingEntity)

    suspend fun deleteAll()

    fun observeSpendingsDetails(): Flow<List<SpendingDetails>>
}