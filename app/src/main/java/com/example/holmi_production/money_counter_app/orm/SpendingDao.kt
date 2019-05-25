package com.example.holmi_production.money_counter_app.orm

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.holmi_production.money_counter_app.model.Spending
import io.reactivex.Single

@Dao
interface SpendingDao{
    @Query("SELECT * FROM Spending")
    fun getAll():Single<List<Spending>>


    @Insert(onConflict = REPLACE)
    fun insert(spending:Spending)
}