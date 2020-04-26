package com.example.holmi_production.money_counter_app.orm

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.holmi_production.money_counter_app.model.entity.SumPerDayEntity
import io.reactivex.Flowable

@Dao
interface SumPerDayDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sumPerDay: SumPerDayEntity)

    @Query("SELECT * FROM SumPerDayEntity WHERE id=:id")
    fun observeSum(id:String):Flowable<SumPerDayEntity>

    @Query("SELECT * FROM SumPerDayEntity WHERE id=:id")
    fun getSum(id:String): SumPerDayEntity
}