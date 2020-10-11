package com.example.holmi_production.money_counter_app.orm

import androidx.room.Dao
import androidx.room.Query
import com.example.holmi_production.money_counter_app.model.entity.CurrenciesEntity
import kotlinx.coroutines.flow.Flow
import org.joda.time.DateTime

@Dao
abstract class CurrencyDao: BaseDao<CurrenciesEntity>(){

    @Query("SELECT * FROM CurrencyTable")
    abstract fun getAll(): Flow<List<CurrenciesEntity>>


    @Query("SELECT * From CurrencyTable WHERE dateId=:date")
    abstract fun getByDate(date: DateTime): CurrenciesEntity

}