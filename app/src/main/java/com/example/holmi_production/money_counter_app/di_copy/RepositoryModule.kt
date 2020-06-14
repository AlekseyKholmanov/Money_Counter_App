package com.example.holmi_production.money_counter_app.di_copy

import com.example.holmi_production.money_counter_app.storage.*
import com.example.holmi_production.money_counter_app.storage.impl.*
import org.koin.dsl.module

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
val repositoryModule = module {

    single<BalanceDatabase> {
        BalanceDatabaseImpl(
            get()
        )
    }

    single<CategoryDatabase> {
        CategoryDatabaseImpl(get())
    }
    single {
        PeriodsDatabaseImpl(
            get()
        )
    }

    single {
        SettingRepository(get(), get())
    }

    single<TransactionDatabase> {
        TransactionDatabaseImpl(
            get()
        )
    }
    single<SubCategoryDatabase> {
        SubCategoryDatabaseImpl(
            get()
        )
    }
    single<SumPerDayDatabase> {
        SumPerDayDatabaseImpl(
            get()
        )
    }

}