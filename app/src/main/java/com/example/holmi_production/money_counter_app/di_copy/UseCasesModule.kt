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

    single<SetRecentCategoryUseCase> {
        SetRecentCategoryUseCaseImpl(
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

    single<CreateAccountUseCase> {
        CreateAccountUseCaseImpl(
            accountDatabase = get()
        )
    }

    single<SaveTransactionUseCase> {
        SaveTransactionUseCaseImpl(
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
}