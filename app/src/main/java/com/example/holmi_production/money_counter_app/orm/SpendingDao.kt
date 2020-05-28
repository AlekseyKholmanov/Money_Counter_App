package com.example.holmi_production.money_counter_app.orm

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.holmi_production.money_counter_app.model.entity.SpendingDetails
import com.example.holmi_production.money_counter_app.model.entity.SpendingEntity
import io.reactivex.Flowable
import io.reactivex.Maybe
import kotlinx.coroutines.flow.Flow
import org.joda.time.DateTime

@Dao
abstract class SpendingDao : BaseDao<SpendingEntity>() {

    @Query("SELECT * FROM SpendingTable")
    abstract fun observeSpending(): Flow<List<SpendingEntity>>

    @Query("SELECT * FROM SpendingTable")
    abstract fun getSpendings(): List<SpendingEntity>

    @Transaction
    @Query("Select * From SpendingTable")
    abstract fun observeSpendingDetails(): Flow<List<SpendingDetails>>

    @Transaction
    @Query("SELECT * FROM SpendingTable WHERE createdDate=:id")
    abstract fun getSpendingWithCategory(id: DateTime): SpendingDetails

    @Query("SELECT * FROM SpendingTable WHERE createdDate =:date")
    abstract fun getSpending(date: DateTime): Maybe<SpendingEntity>

    @Query("SELECT * FROM SpendingTable WHERE isSpending != 0")
    abstract fun getSpentSum(): List<SpendingEntity>

    @Query("SELECT * FROM SpendingTable WHERE isSpending == 0")
    abstract fun getIncomeSum(): List<SpendingEntity>

    @Query("DELETE FROM SpendingTable")
    abstract fun deleteAll()
}