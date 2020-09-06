package com.example.holmi_production.money_counter_app.storage.db

import com.example.holmi_production.money_counter_app.model.entity.BalanceEntity
import kotlinx.coroutines.flow.Flow
import org.joda.time.DateTime

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
interface BalanceDatabase {

    suspend fun insert(balance: BalanceEntity)

    suspend fun getBalaceByDate(date: DateTime): BalanceEntity

    fun observeBalances(): Flow<List<BalanceEntity>>
}