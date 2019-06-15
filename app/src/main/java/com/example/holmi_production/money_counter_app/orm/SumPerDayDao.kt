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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sumPerDayList: Collection<SumPerDay>)

    @Query("SELECT * FROM SumPerDay")
    fun observeSumPerDay():Flowable<List<SumPerDay>>

    @Query("SELECT * FROM SumPerDay WHERE dateTime=:dateTime")
    fun getOnDate(dateTime: DateTime): SumPerDay

    @Query("SELECT * FROM SumPerDay WHERE dateTime>=:dateTime")
    fun getPeriodFrom(dateTime: DateTime): List<SumPerDay>
}