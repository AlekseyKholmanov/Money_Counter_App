package com.example.holmi_production.money_counter_app.orm

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.holmi_production.money_counter_app.model.Period
import io.reactivex.Single

@Dao
interface PeriodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(period: Period)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(periods:List<Period>)

    @Query("Select * from Period where id=:key")
    fun getPeriod(key:String):Single<Period>

    @Query("Select * from Period")
    fun getAll(): Single<List<Period>>
}