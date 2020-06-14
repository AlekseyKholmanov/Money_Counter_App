package com.example.holmi_production.money_counter_app.orm

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.holmi_production.money_counter_app.model.entity.*

@TypeConverters(Converters::class)
@Database(
    entities = [
        TransactionEntity::class,
        SumPerDayEntity::class,
        FilterPeriodEntity::class,
        CategoryEntity::class,
        SubCategoryEntity::class,
        BalanceEntity::class,
        AccountEntity::class,
        RecentCategoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ExpenseDatabase : RoomDatabase() {

    abstract fun spendingDao(): TransactionDao
    abstract fun sumPerDayDao(): SumPerDayDao
    abstract fun periodsDao(): PeriodsDao
    abstract fun categoryDao(): CategoryDao
    abstract fun subCategoryDao(): SubcategoryDao
    abstract fun balanceDao(): BalanceDao
    abstract fun accountDao(): AccountDao
    abstract fun recentCategoryDao(): RecentCategoryDao

}