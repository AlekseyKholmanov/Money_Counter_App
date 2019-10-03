package com.example.holmi_production.money_counter_app.orm

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.holmi_production.money_counter_app.model.entity.SumPerDay
import io.reactivex.Flowable

@Dao
interface SumPerDayDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sumPerDay: SumPerDay)

    @Query("SELECT * FROM SumPerDay WHERE id=:id")
    fun observeSum(id:String):Flowable<SumPerDay>

    @Query("SELECT * FROM SumPerDay WHERE id=:id")
    fun getSum(id:String): SumPerDay
}