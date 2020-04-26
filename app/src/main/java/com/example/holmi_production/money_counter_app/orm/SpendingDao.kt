package com.example.holmi_production.money_counter_app.orm

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.holmi_production.money_counter_app.model.entity.SpendingEntity
import com.example.holmi_production.money_counter_app.model.entity.SpendingWithCategory
import io.reactivex.Flowable
import io.reactivex.Maybe
import org.joda.time.DateTime

@Dao
interface SpendingDao {
    @Query("SELECT * FROM SpendingEntity")
    fun observeSpending(): Flowable<List<SpendingEntity>>

    @Query("SELECT * FROM SpendingEntity")
    fun getSpendings():List<SpendingEntity>

    @Query("Select * From SpendingEntity")
    fun observeSpendingWithCategory():List<SpendingWithCategory>


    @Query("SELECT * FROM SpendingEntity WHERE createdDate=:id")
    fun getSpendingWithCategory(id:DateTime):SpendingWithCategory

    @Query("SELECT * FROM SpendingEntity WHERE createdDate =:date")
    fun getSpending(date:DateTime): Maybe<SpendingEntity>

    @Insert(onConflict = REPLACE)
    fun insert(spending: SpendingEntity)

    @Query("SELECT * FROM SpendingEntity WHERE isSpending != 0")
    fun getSpentSum(): List<SpendingEntity>

    @Query("SELECT * FROM SpendingEntity WHERE isSpending == 0")
    fun getIncomeSum(): List<SpendingEntity>

    @Delete
    fun delete(spending: SpendingEntity)

    @Query("DELETE FROM SpendingEntity")
    fun deleteAll()
}