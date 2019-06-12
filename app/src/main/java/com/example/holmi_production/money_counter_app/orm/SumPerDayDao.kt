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
    fun insert(sumPerDayList: List<SumPerDay>)

    @Query("Select * from SumPerDay where dateTime=:dateTime")
    fun getByDate(dateTime: DateTime): Flowable<SumPerDay>

    @Query("SELECT * FROM SumPerDay WHERE dateTime>=:dateTime")
    fun getFromDate(dateTime: DateTime): Flowable<List<SumPerDay>>
}