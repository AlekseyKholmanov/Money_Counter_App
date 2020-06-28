package com.example.holmi_production.money_counter_app.orm

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.holmi_production.money_counter_app.model.entity.FilterPeriodEntity
import io.reactivex.Flowable
import kotlinx.coroutines.flow.Flow

@Dao
interface PeriodsDao {
    @Query("SELECT * FROM FilterPeriodEntity WHERE id=:key")
    fun observePeriod(key:String): Flow<FilterPeriodEntity?>

    @Query("SELECT * FROM FilterPeriodEntity WHERE id=:key")
    fun getPeriod(key:String): FilterPeriodEntity

    @Insert(onConflict = REPLACE)
    fun insert(date: FilterPeriodEntity)
}

