package com.example.holmi_production.money_counter_app.di_copy

import androidx.room.Room
import com.example.holmi_production.money_counter_app.orm.ExpenseDatabase
import com.example.holmi_production.money_counter_app.storage.*
import com.example.holmi_production.money_counter_app.storage.impl.*
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
val databaseModule = module {

    val DATABASE_NAME = "moneyCounterDb"

    single {
        Room.databaseBuilder(androidApplication(), ExpenseDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .fallbackToDestructiveMigrationOnDowngrade()
            .build()
    }

    single<BalanceDatabase> {
        BalanceDatabaseImpl(
            dao = get()
        )
    }

    single<CategoryDatabase> {
        CategoryDatabaseImpl(
            dao = get()
        )
    }
    single<PeriodsDatabase> {
        PeriodsDatabaseImpl(
            dao = get()
        )
    }

    single {
        AppPreference(get(), get())
    }

    single<TransactionDatabase> {
        TransactionDatabaseImpl(
            dao = get()
        )
    }

    single<SubCategoryDatabase> {
        SubCategoryDatabaseImpl(
            dao = get()
        )
    }

    single<SumPerDayDatabase> {
        SumPerDayDatabaseImpl(
            dao = get()
        )
    }

    single<AccountDatabase> {
        AccountDatabaseImpl(
            dao = get()
        )
    }
    single<RecentCategoryDatabase> {
        RecentCategoryDatabaseImpl(
            dao = get()
        )
    }

}