package com.example.holmi_production.money_counter_app.di.modules

import com.example.holmi_production.money_counter_app.di.common.PerFeature
import com.example.holmi_production.money_counter_app.interactor.BalanceInteractor
import com.example.holmi_production.money_counter_app.interactor.CategoryInteractor
import com.example.holmi_production.money_counter_app.interactor.SpendingInteractor
import com.example.holmi_production.money_counter_app.storage.PeriodsRepository
import com.example.holmi_production.money_counter_app.storage.SettingRepository
import com.example.holmi_production.money_counter_app.storage.SpendingRepository
import com.example.holmi_production.money_counter_app.storage.SumPerDayRepository
import com.example.holmi_production.money_counter_app.ui.presenters.*
import com.example.holmi_production.money_counter_app.ui.presenters.charts.ChartBalanceViewModel
import com.example.holmi_production.money_counter_app.ui.presenters.charts.ChartPieViewModel
import com.example.holmi_production.money_counter_app.ui.presenters.charts.ChartStackedPresenter
import dagger.Module
import dagger.Provides

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
@Module
object PresenterModule {


    @Provides
    @PerFeature
    fun provideCostsPresenter(
        spendingInteractor: SpendingInteractor
    ): CostsPresenter {
        return CostsPresenter(spendingInteractor)
    }

    @Provides
    @PerFeature
    fun provideCreateCategoryPresenter(
        interactor: CategoryInteractor
    ): CreateCategoryPresenter {
        return CreateCategoryPresenter(interactor)
    }

    @Provides
    @PerFeature
    fun provideEditCategoryPresenter(
        interactor: CategoryInteractor
    ): EditCategoryPresenter {
        return EditCategoryPresenter(interactor)
    }

    @Provides
    @PerFeature
    fun provideEndPeriodPresenter(
        settingRepository: SettingRepository,
        spendingInteractor: SpendingInteractor
    ): EndPeriodPresenter {
        return EndPeriodPresenter(
            settingRepository,
            spendingInteractor
        )
    }

    @Provides
    @PerFeature
    fun provideFirstLaunchPresenter(
        spendingRepository: SpendingRepository,
        sumPerDayRepository: SumPerDayRepository,
        settingRepository: SettingRepository
    ): FirstLaunchPresenter {
        return FirstLaunchPresenter(
            spendingRepository,
            sumPerDayRepository,
            settingRepository
        )
    }

    @Provides
    @PerFeature
    fun provideKeyboardPresenter(
        sumPerDayRepository: SumPerDayRepository,
        settingRepository: SettingRepository,
        spendingInteractor: SpendingInteractor,
        categoryInteractor: CategoryInteractor,
        spendingRepository: SpendingRepository
    ): KeyboardPresenter {
        return KeyboardPresenter(
            sumPerDayRepository,
            settingRepository,
            spendingInteractor,
            categoryInteractor,
            spendingRepository
        )
    }

    @Provides
    @PerFeature
    fun provideLimitsPresenter(): LimitsPresenter {
        return LimitsPresenter()
    }

    @Provides
    @PerFeature
    fun provideSettingsPresenter(
        spendingInteractor: SpendingInteractor,
        categoryInteractor: CategoryInteractor,
        settingRepository: SettingRepository
    ): SettingsPresenter {
        return SettingsPresenter(
            spendingInteractor,
            categoryInteractor,
            settingRepository
        )
    }

    @Provides
    @PerFeature
    fun provideTopbarPresenter(
        periodsRepository: PeriodsRepository,
        settingRepository: SettingRepository
    ): TopbarViewModel {
        return TopbarViewModel(
            periodsRepository,
            settingRepository
        )
    }


    @Provides
    @PerFeature
    fun provideChartBalancePresenter(
        balanceInteractor: BalanceInteractor
    ): ChartBalanceViewModel {
        return ChartBalanceViewModel(balanceInteractor)
    }

    @Provides
    @PerFeature
    fun provideChartPiePresenter(
        spendingInteractor: SpendingInteractor
    ): ChartPieViewModel {
        return ChartPieViewModel(spendingInteractor)
    }

    @Provides
    @PerFeature
    fun provideStackedPresenter(
        spendingInteractor: SpendingInteractor
    ): ChartStackedPresenter {
        return ChartStackedPresenter(spendingInteractor)
    }
}