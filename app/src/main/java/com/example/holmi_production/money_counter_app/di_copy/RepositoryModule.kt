package com.example.holmi_production.money_counter_app.di_copy

import com.example.holmi_production.money_counter_app.storage.*
import com.example.holmi_production.money_counter_app.storage.impl.*
import org.koin.dsl.module

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
val repositoryModule = module {

    single {
        BalanceDatabaseImpl(
            get()
        )
    }
    single {
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
    single {
        SpendingDatabaseImpl(
            get()
        )
    }
    single {
        SubCategoryDatabaseImpl(
            get()
        )
    }
    single {
        SumPerDayDatabaseImpl(
            get()
        )
    }

}