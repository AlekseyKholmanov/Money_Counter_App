package com.example.holmi_production.money_counter_app.di_copy

import com.example.holmi_production.money_counter_app.ui.viewModels.BottomKeyboardViewModel
import com.example.holmi_production.money_counter_app.ui.viewModels.KeyboardViewModel
import com.example.holmi_production.money_counter_app.ui.viewModels.SelectCategoryViewModel
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
            interactor = get(),
            setRecentCategoryUseCase = get()
        )
    }

    viewModel {
        BottomKeyboardViewModel(
            getRecentCategoryUseCase = get()
        )
    }
}