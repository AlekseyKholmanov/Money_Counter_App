package com.example.holmi_production.money_counter_app.orm

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.holmi_production.money_counter_app.model.Spending

@TypeConverters(Converters::class)
@Database(
    entities = [Spending::class],
    version = 1,
    exportSchema = false
)
abstract class ExpenseDatabase : RoomDatabase() {
    abstract fun spendingDao(): SpendingDao
}