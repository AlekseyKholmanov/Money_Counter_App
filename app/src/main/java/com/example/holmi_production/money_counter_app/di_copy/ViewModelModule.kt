package com.example.holmi_production.money_counter_app.di_copy

import com.example.holmi_production.money_counter_app.ui.viewModels.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
val viewModelsModule = module {
    viewModel {
        KeyboardViewModel(
            spendingInteractor = get(),
            sumPerDayDatabase = get(),
            transactionDatabase = get()
        )
    }

    viewModel {
        SelectCategoryViewModel(
            getCategoriesUseCase = get(),
            addRecentCategoryUseCase = get()
        )
    }

    viewModel {
        BottomKeyboardViewModel(
            getRecentCategoryUseCase = get()
        )
    }


    viewModel {
        CategoryDetailsViewModel(
            getCategoryUseCase = get(),
            addCategoryUseCase = get()
        )
    }

    viewModel {
        DashboardViewModel(
            getAccountsUseCase = get()
        )
    }

    viewModel {
        CreateAccountViewModel(
            appPreference = get(),
            createAccountViewModel = get()
        )
    }

    viewModel {
        OnBoardingViewModel(
            createAccountViewModel = get(),
            appPreference = get()
        )
    }

    viewModel {
        SimpleBottomKeyboardViewModel(
            getRecentCategoryUseCase = get(),
            addTransactionUseCase = get()
        )
    }

    viewModel {
        TransactionViewModel(
            getTransactionUseCase = get(),
            editTransactionUSeCase = get(),
            getLatestActivePeriodUseCase = get(),
            addActivePeriodUseCase = get()
        )
    }
}