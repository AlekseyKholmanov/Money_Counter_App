package com.example.holmi_production.money_counter_app.di_copy

import com.example.holmi_production.money_counter_app.useCases.*
import com.example.holmi_production.money_counter_app.useCases.impl.*
import org.koin.dsl.module

val useCasesModule = module {

    single<GetRecentCategoryUseCase> {
        GetRecentCategoryUseCaseImpl(
            recentDatabase = get(),
            categoriesDatabase = get()
        )
    }

    single<AddRecentCategoryUseCase> {
        AddRecentCategoryUseCaseImpl(
            recentCategoryDatabase = get()
        )
    }

    single<GetCategoriesUseCase> {
        GetCategoriesUseCaseImpl(
            categoriesDatabase = get()
        )
    }

    single<GetCategoryUseCase> {
        GetCategoryUseCaseImpl(
            categoryDatabase = get()
        )
    }

    single<AddCategoryUseCase> {
        AddCategoryUseCaseImpl(
            categoryDatabase = get()
        )
    }

    single<GetAccountsUseCase> {
        GetAccountsUseCaseImpl(
            accountDatabase = get()
        )
    }

    single<AddAccountUseCase> {
        AddAccountUseCaseImpl(
            accountDatabase = get()
        )
    }

    single<AddTransactionUseCase> {
        AddTransactionUseCaseImpl(
            categoryDatabase = get(),
            transactionDatabase = get()
        )
    }

    single<GetTransactionUseCase> {
        GetTransactionUseCaseImpl(
            transactionDatabase = get()
        )
    }

    single<EditTransactionUseCase> {
        EditTransactionUseCaseImpl(
            transactionDatabase = get()
        )
    }

    single<GetLatestActivePeriodUseCase> {
        GetLatestActivePeriodUseCaseImpl(
            periodsDatabase = get()
        )
    }

    single<AddActivePeriodUseCase> {
        AddActivePeriodUseCaseImpl(
            periodsDatabase = get()
        )
    }

    single<AddSubcategoryUseCase> {
        AddSubcategoryUseCaseImpl(
            db = get()
        )
    }

    single<GetSubcategoryUseCase> {
        GetSubcategoryUseCaseImpl(
            db = get()
        )
    }

    single<EditSubcategoryUseCase> {
        EditSubcategoryUseCaseImpl(
            db = get()
        )
    }
}