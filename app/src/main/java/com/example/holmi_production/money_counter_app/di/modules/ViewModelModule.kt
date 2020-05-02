package com.example.holmi_production.money_counter_app.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.holmi_production.money_counter_app.di.common.PerFeature
import com.example.holmi_production.money_counter_app.di.common.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Provider

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
@Module
object ViewModelModule {

    @Provides
    @PerFeature
    fun provideViewModelFactory(viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>): ViewModelProvider.Factory {
        return ViewModelFactory(viewModels)
    }

}