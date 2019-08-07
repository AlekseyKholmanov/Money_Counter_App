package com.example.holmi_production.money_counter_app.orm

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.holmi_production.money_counter_app.model.FilterPeriods
import com.example.holmi_production.money_counter_app.model.Spending
import com.example.holmi_production.money_counter_app.model.SumPerDay

@TypeConverters(Converters::class)
@Database(
    entities = [Spending::class, SumPerDay::class, FilterPeriods::class],
    version = 14,
    exportSchema = false
)
abstract class ExpenseDatabase : RoomDatabase() {
    abstract val spendingDao: SpendingDao
    abstract val sumPerDayDao: SumPerDayDao
    abstract val periodsDao:PeriodsDao
}