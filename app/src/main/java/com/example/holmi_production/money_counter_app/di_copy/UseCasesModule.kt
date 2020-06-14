package com.example.holmi_production.money_counter_app.di_copy

import com.example.holmi_production.money_counter_app.storage.BalanceDatabase
import com.example.holmi_production.money_counter_app.storage.impl.BalanceDatabaseImpl
import com.example.holmi_production.money_counter_app.useCases.GetCategoryUseCase
import com.example.holmi_production.money_counter_app.useCases.GetRecentCategoryUseCase
import com.example.holmi_production.money_counter_app.useCases.SetRecentCategoryUseCase
import com.example.holmi_production.money_counter_app.useCases.impl.GetCategoryUseCaseImpl
import com.example.holmi_production.money_counter_app.useCases.impl.GetRecentCategoryUseCaseImpl
import com.example.holmi_production.money_counter_app.useCases.impl.SetRecentCategoryUseCaseImpl
import org.koin.dsl.module

val useCasesModule = module{

    single<GetCategoryUseCase> {
        GetCategoryUseCaseImpl(
            categoryDb = get()
        )
    }

    single<GetRecentCategoryUseCase> {
        GetRecentCategoryUseCaseImpl(
            categoriesDb = get(),
            recentDb = get()
        )
    }

    single<SetRecentCategoryUseCase> {
        SetRecentCategoryUseCaseImpl(
            recentCategoryDatabase = get()
        )
    }
}