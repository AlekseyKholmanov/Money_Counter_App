package com.example.holmi_production.money_counter_app.di

import ChartPieViewModel
import com.example.holmi_production.money_counter_app.ui.LaunchViewModel
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
            getCategoriesUseCase = get(),
            getAccountUseCase = get()
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
            lastAccountManager =    get(),
            getCurrenciesCourseUseCase = get()
        )
    }

    viewModel {
        CreateAccountViewModel(
            appPreference = get(),
            addTransactionUseCase = get(),
            createAccountViewModel = get(),
            settingsManager = get()
        )
    }

    viewModel {
        OnBoardingViewModel(
            createAccountViewModel = get(),
            appPreference = get()
        )
    }

    viewModel {
        ChartViewModel(
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
            updateActivePeriodUseCase = get(),
            fetchCurrencyUseCase = get()
        )
    }

    viewModel {
        LaunchViewModel(
            onboardingCompletedUseCase = get()
        )
    }

    viewModel {
        SettingsViewModel(
            getTransactionUseCase = get()
        )
    }
}