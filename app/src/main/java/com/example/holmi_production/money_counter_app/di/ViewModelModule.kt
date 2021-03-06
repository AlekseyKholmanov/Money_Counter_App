package com.example.holmi_production.money_counter_app.di

import ChartPieViewModel
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
            getRecentCategoryUseCase = get(),
            addTransactionUseCase = get(),
            getCategoriesUseCase = get()
        )
    }


    viewModel {
        CategoryDetailsViewModel(
            getCategoryUseCase = get(),
            addCategoryUseCase = get(),
            addSubcategoryUseCase = get(),
            editSubcategoryUseCase = get()
        )
    }

    viewModel {
        DashboardViewModel(
            getAccountsUseCase = get(),
            getTransactionUseCase = get(),
            settingsManager = get()
        )
    }

    viewModel {
        CreateAccountViewModel(
            appPreference = get(),
            addTransactionUseCase = get(),
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
            getActivePeriodUseCase = get(),
            updateActivePeriodUseCase = get()
        )
    }

    viewModel {
        ChartPieViewModel(
            spendingInteractor = get()
        )
    }

    viewModel {
        ChartBalanceViewModel(
            balanceInteractor = get()
        )
    }

    viewModel {
        MainViewModel(
            getActivePeriodUseCase = get(),
            updateActivePeriodUseCase = get()
        )
    }
}