package com.example.holmi_production.money_counter_app.di_copy

import com.example.holmi_production.money_counter_app.ui.presenters.SelectCategoryViewModel
import com.example.holmi_production.money_counter_app.ui.view_models.KeyboardViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
val viewModelsModule = module {
    viewModel { KeyboardViewModel(get(), get(), get(), get(), get()) }
    viewModel { SelectCategoryViewModel(get(), get()) }
}