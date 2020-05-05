package com.example.holmi_production.money_counter_app.di_copy

import com.example.holmi_production.money_counter_app.storage.*
import com.example.holmi_production.money_counter_app.storage.impl.CategoryRepositoryImpl
import org.koin.dsl.module

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
val repositoryModule = module {

    single {
        BalanceRepository(get())
    }
    single {
        CategoryRepositoryImpl(get())
    }
    single {
        PeriodsRepository(get())
    }
    single {
        SettingRepository(get(), get())
    }
    single {
        SpendingRepository(get())
    }
    single {
        SubCategoryRepository(get())
    }
    single {
        SumPerDayRepository(get())
    }

}