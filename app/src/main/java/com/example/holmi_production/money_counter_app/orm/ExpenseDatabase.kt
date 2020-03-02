package com.example.holmi_production.money_counter_app.orm

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.holmi_production.money_counter_app.model.entity.*

@TypeConverters(Converters::class)
@Database(
    entities = [Spending::class, SumPerDay::class, FilterPeriods::class, Category::class, SubCategory::class, Balance::class],
    version = 1,
    exportSchema = false
)
abstract class ExpenseDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private var instance: ExpenseDatabase? = null

        private val DATABASE_NAME = "money_counter_db"

        fun getInstance(context: Context): ExpenseDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): ExpenseDatabase {
            return Room.databaseBuilder(context, ExpenseDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract val spendingDao: SpendingDao
    abstract val sumPerDayDao: SumPerDayDao
    abstract val periodsDao: PeriodsDao
    abstract val categoryDao: CategoryDao
    abstract val subCategoryDao: SubcategoryDao
    abstract val balanceDao: BalanceDao
}