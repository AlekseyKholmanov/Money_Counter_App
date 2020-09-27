package com.example.holmi_production.money_counter_app.orm

import androidx.room.Dao
import androidx.room.Query
import androidx.room.RoomWarnings
import androidx.room.Transaction
import com.example.holmi_production.money_counter_app.model.TransactionDetails
import com.example.holmi_production.money_counter_app.model.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow
import org.joda.time.DateTime

@Dao
abstract class TransactionDao : BaseDao<TransactionEntity>() {

    @Query("SELECT * FROM TransactionTable WHERE isDeleted = 0")
    abstract fun observeTransactions(): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM TransactionTable ")
    abstract fun getTransactions(): List<TransactionEntity>

    @Transaction
    @Query("SELECT * FROM TransactionTable WHERE isDeleted = 0 ORDER BY createdDate DESC")
    abstract fun observeTransactionDetails(): Flow<List<TransactionDetails>>

    @Query("""
        SELECT * FROM TransactionTable, PeriodTable
        WHERE createdDate >= `from` 
        AND createdDate <= `to`
        AND isDeleted = 0
    """)
    abstract fun observeTransactionsWithPeriod(): Flow<List<TransactionEntity>>

    @Transaction
    @Query("""
        SELECT TransactionTable.* FROM TransactionTable, PeriodTable
        WHERE createdDate >= PeriodTable.`from`
        AND createdDate <= PeriodTable.`to`
        AND isDeleted = 0
    """)
    abstract fun observeTransactionDetailsWithPeriod(): Flow<List<TransactionDetails>>

    @Query("SELECT * FROM TransactionTable WHERE sum < 0")
    abstract fun getSpentSum(): List<TransactionEntity>

    @Query("SELECT * FROM TransactionTable WHERE sum > 0")
    abstract fun getIncomeSum(): List<TransactionEntity>

    @Query("DELETE FROM TransactionTable")
    abstract fun deleteAll()

    @Query("UPDATE TransactionTable SET isDeleted = 1 WHERE id=:transactionId ")
    abstract fun markDeleted(transactionId: String)
}