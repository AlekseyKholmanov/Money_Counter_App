package com.example.holmi_production.money_counter_app.di.modules

import androidx.lifecycle.ViewModel
import com.example.holmi_production.money_counter_app.di.common.PerFeature
import com.example.holmi_production.money_counter_app.di.common.ViewModelKey
import com.example.holmi_production.money_counter_app.interactor.CategoryInteractor
import com.example.holmi_production.money_counter_app.interactor.SpendingInteractor
import com.example.holmi_production.money_counter_app.storage.SettingRepository
import com.example.holmi_production.money_counter_app.storage.SpendingRepository
import com.example.holmi_production.money_counter_app.storage.SumPerDayRepository
import com.example.holmi_production.money_counter_app.ui.view_models.KeyboardViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(KeyboardViewModel::class)
    abstract fun provideKeyboardViewModel(
        viewModel: KeyboardViewModel
    ): ViewModel

}