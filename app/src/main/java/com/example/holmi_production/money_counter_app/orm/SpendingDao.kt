package com.example.holmi_production.money_counter_app.orm

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.holmi_production.money_counter_app.model.Spending
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import org.joda.time.DateTime

@Dao
interface SpendingDao {
    @Query("SELECT * FROM Spending")
    fun observeSpending(): Flowable<List<Spending>>

    @Query("SELECT * FROM Spending")
    fun getSpendings():List<Spending>

    @Query("SELECT * FROM Spending WHERE spendingDate =:date")
    fun getSpending(date:DateTime): Maybe<Spending>

    @Insert(onConflict = REPLACE)
    fun insert(spending: Spending)

    @Query("SELECT sum FROM Spending WHERE categoryTypes != 0")
    fun getSpentSum(): List<Float>

    @Query("SELECT sum FROM Spending WHERE categoryTypes == 0")
    fun getIncomeSum(): List<Float>

    @Delete
    fun delete(spending: Spending)
}