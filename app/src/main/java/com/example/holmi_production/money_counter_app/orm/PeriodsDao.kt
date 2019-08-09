package com.example.holmi_production.money_counter_app.orm

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.holmi_production.money_counter_app.model.FilterPeriods
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface PeriodsDao {
    @Query("SELECT * FROM FilterPeriods WHERE id=:key")
    fun observePeriod(key:String):Flowable<FilterPeriods>

    @Query("SELECT * FROM FilterPeriods WHERE id=:key")
    fun getPeriod(key:String): FilterPeriods

    @Insert(onConflict = REPLACE)
    fun insert(date:FilterPeriods)
}

