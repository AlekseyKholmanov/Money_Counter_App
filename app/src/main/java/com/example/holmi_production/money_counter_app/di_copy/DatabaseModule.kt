package com.example.holmi_production.money_counter_app.di_copy

import androidx.room.Room
import com.example.holmi_production.money_counter_app.orm.ExpenseDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
val DATABASE_NAME = "moneyCounterDb"

val databaseModule = module {
    single {
        Room.databaseBuilder(androidApplication(), ExpenseDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<ExpenseDatabase>().balanceDao() }
    single { get<ExpenseDatabase>().categoryDao() }
    single { get<ExpenseDatabase>().periodsDao() }
    single { get<ExpenseDatabase>().spendingDao() }
    single { get<ExpenseDatabase>().subCategoryDao() }
    single { get<ExpenseDatabase>().sumPerDayDao() }
}