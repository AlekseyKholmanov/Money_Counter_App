package com.example.holmi_production.money_counter_app.orm

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.holmi_production.money_counter_app.model.Spending
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface SpendingDao {
    @Query("SELECT * FROM Spending")
    fun getAll(): Flowable<List<Spending>>

    @Insert(onConflict = REPLACE)
    fun insert(spending: Spending)

    @Query("Select sum FROM Spending Where categoryTypes != 0")
    fun getSpentSum(): Flowable<List<Float>>

    @Query("Select sum from Spending where categoryTypes == 0")
    fun getIncomeSum(): Flowable<List<Float>>

    @Delete
    fun delete(spending: Spending)
}