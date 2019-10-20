package com.example.holmi_production.money_counter_app.orm

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.holmi_production.money_counter_app.model.entity.*
import com.example.holmi_production.money_counter_app.storage.PeriodsRepository
import org.joda.time.DateTime
import java.util.concurrent.Executors

@TypeConverters(Converters::class)
@Database(
    entities = [Spending::class, SumPerDay::class, FilterPeriods::class, Category::class, SubCategory::class],
    version = 18,
    exportSchema = false
)
abstract class ExpenseDatabase : RoomDatabase() {
    companion object {
        private var INSTANCE: ExpenseDatabase? = null

        private val DATABASE_NAME = "money_counter_db"

        fun getInstance(context: Context): ExpenseDatabase? {
            if (INSTANCE == null) {
                INSTANCE = buildDatabase(context)
            }
            return INSTANCE
        }

        private fun buildDatabase(context: Context): ExpenseDatabase {
            return Room.databaseBuilder(context, ExpenseDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .addCallback(
                    object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            Log.d("M_ExpenseDatabase","start execution")
                            Executors.newSingleThreadScheduledExecutor()
                                .execute {
                                    getInstance(context)!!.periodsDao.insert(
                                        FilterPeriods(
                                            PeriodsRepository.key,
                                            DateTime().minusDays(6).withTimeAtStartOfDay(),
                                            DateTime().withTime(23, 59, 59, 59)
                                        )
                                    )
                                }
                            Log.d("M_ExpenseDatabase", "pre-populated")
                        }
                    }
                )
                .build()
        }

    }

    abstract val spendingDao: SpendingDao
    abstract val sumPerDayDao: SumPerDayDao
    abstract val periodsDao: PeriodsDao
    abstract val categoryDao: CategoryDao
    abstract val subCategoryDao:SubcategoryDao
}