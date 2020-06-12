package com.example.holmi_production.money_counter_app.orm

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.holmi_production.money_counter_app.model.TransactionDetails
import com.example.holmi_production.money_counter_app.model.entity.TransactionEntity
import io.reactivex.Maybe
import kotlinx.coroutines.flow.Flow
import org.joda.time.DateTime

@Dao
abstract class TransactionDao : BaseDao<TransactionEntity>() {

    @Query("SELECT * FROM TransactionTable")
    abstract fun observeSpending(): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM TransactionTable")
    abstract fun getSpendings(): List<TransactionEntity>

    @Transaction
    @Query("Select * From TransactionTable")
    abstract fun observeSpendingDetails(): Flow<List<TransactionDetails>>

    @Transaction
    @Query("SELECT * FROM TransactionTable WHERE createdDate=:id")
    abstract fun getSpendingWithCategory(id: DateTime): TransactionDetails

    @Query("SELECT * FROM TransactionTable WHERE createdDate =:date")
    abstract fun getSpending(date: DateTime): Maybe<TransactionEntity>

    @Query("SELECT * FROM TransactionTable WHERE sum < 0")
    abstract fun getSpentSum(): List<TransactionEntity>

    @Query("SELECT * FROM TransactionTable WHERE sum > 0")
    abstract fun getIncomeSum(): List<TransactionEntity>

    @Query("DELETE FROM TransactionTable")
    abstract fun deleteAll()
}