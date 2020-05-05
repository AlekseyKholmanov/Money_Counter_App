package com.example.holmi_production.money_counter_app.di.modules

import androidx.lifecycle.ViewModelProvider
import com.example.holmi_production.money_counter_app.di.common.ViewModelFactory
import dagger.Binds
import dagger.Module

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}