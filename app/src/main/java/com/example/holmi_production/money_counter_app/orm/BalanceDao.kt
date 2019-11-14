package com.example.holmi_production.money_counter_app.orm

import androidx.room.*
import com.example.holmi_production.money_counter_app.model.entity.Balance
import io.reactivex.Flowable
import org.joda.time.DateTime

@Dao
interface BalanceDao {

    @Query("SELECT * FROM Balance")
    fun getBalances():Flowable<List<Balance>>

    @Query("SELECT * FROM Balance WHERE id=:id")
    fun getBalance(id:DateTime): Balance

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(balance: Balance)

    @Delete
    fun delete(balance: Balance)

    @Query("DELETE FROM Balance")
    fun deleteAll()
}