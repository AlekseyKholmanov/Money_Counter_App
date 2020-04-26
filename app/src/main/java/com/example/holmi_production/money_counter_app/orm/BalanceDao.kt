package com.example.holmi_production.money_counter_app.orm

import androidx.room.*
import com.example.holmi_production.money_counter_app.model.entity.BalanceEntity
import io.reactivex.Flowable
import org.joda.time.DateTime

@Dao
interface BalanceDao {

    @Query("SELECT * FROM BalanceEntity")
    fun getBalances():Flowable<List<BalanceEntity>>

    @Query("SELECT * FROM BalanceEntity WHERE id=:id")
    fun getBalance(id:DateTime): BalanceEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(balance: BalanceEntity)

    @Delete
    fun delete(balance: BalanceEntity)

    @Query("DELETE FROM BalanceEntity")
    fun deleteAll()
}