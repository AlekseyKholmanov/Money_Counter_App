package com.example.holmi_production.money_counter_app.orm

import androidx.room.*
import com.example.holmi_production.money_counter_app.model.entity.BalanceEntity
import io.reactivex.Flowable
import kotlinx.coroutines.flow.Flow
import org.joda.time.DateTime

@Dao
interface BalanceDao {

    @Query("SELECT * FROM BalanceTable")
    fun getBalances(): Flow<List<BalanceEntity>>

    @Query("SELECT * FROM BalanceTable WHERE id=:id")
    fun getBalance(id:DateTime): BalanceEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(balance: BalanceEntity)

    @Delete
    fun delete(balance: BalanceEntity)

    @Query("DELETE FROM BalanceTable")
    fun deleteAll()
}