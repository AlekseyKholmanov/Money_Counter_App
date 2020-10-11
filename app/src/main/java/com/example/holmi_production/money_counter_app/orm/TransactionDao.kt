package com.example.holmi_production.money_counter_app.orm

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.holmi_production.money_counter_app.model.TransactionDetails
import com.example.holmi_production.money_counter_app.model.dto.TransactionDetailsDTO
import com.example.holmi_production.money_counter_app.model.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TransactionDao : BaseDao<TransactionEntity>() {

    @Query("SELECT * FROM TransactionTable WHERE isDeleted = 0")
    abstract fun observeTransactions(): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM TransactionTable ")
    abstract fun getTransactions(): List<TransactionEntity>

    @Query(
        """
        SELECT * FROM TransactionTable, PeriodTable
        WHERE createdDate >= `from` 
        AND createdDate <= `to`
        AND isDeleted = 0
    """
    )
    abstract fun observeTransactionsWithPeriod(): Flow<List<TransactionEntity>>


    @Query("SELECT * FROM TransactionTable WHERE sum < 0")
    abstract fun getSpentSum(): List<TransactionEntity>

    @Query("SELECT * FROM TransactionTable WHERE sum > 0")
    abstract fun getIncomeSum(): List<TransactionEntity>

    @Query("DELETE FROM TransactionTable")
    abstract fun deleteAll()

    @Query("UPDATE TransactionTable SET isDeleted = 1 WHERE id=:transactionId ")
    abstract fun markDeleted(transactionId: String)

    @Query(
        """
        SELECT * FROM TransactionTable, PeriodTable 
        WHERE createdDate >= PeriodTable.`from`
        AND createdDate <= PeriodTable.`to`
        AND isDeleted = 0 
        AND accountId=:accountId
        ORDER BY createdDate
        """
    )
    abstract fun observeDetailsTransactionByAccountId(accountId: String): Flow<List<TransactionDetailsDTO>>

    @Query(
        """
        SELECT * FROM TransactionTable, PeriodTable 
        WHERE createdDate >= PeriodTable.`from`
        AND createdDate <= PeriodTable.`to`
        AND isDeleted = 0 
        ORDER BY createdDate
        """
    )
    abstract fun observeDetailsTransaction(): Flow<List<TransactionDetailsDTO>>

    @Query("SELECT COUNT(*) FROM TransactionTable")
    abstract fun observeTransactionCount(): Flow<Int>
}