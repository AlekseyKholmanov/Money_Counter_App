package com.example.holmi_production.money_counter_app.orm

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.holmi_production.money_counter_app.model.SumPerDay
import io.reactivex.Flowable
import io.reactivex.Single
import org.joda.time.DateTime

@Dao
interface SumPerDayDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sumPerDay: SumPerDay)

    @Query("SELECT * FROM SumPerDay WHERE id=:id")
    fun observeSum(id:String):Flowable<SumPerDay>

    @Query("SELECT * FROM SumPerDay WHERE id=:id")
    fun getSum(id:String): SumPerDay
}