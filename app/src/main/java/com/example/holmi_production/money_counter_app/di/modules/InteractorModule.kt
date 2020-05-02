package com.example.holmi_production.money_counter_app.di.modules

import com.example.holmi_production.money_counter_app.di.common.PerFeature
import com.example.holmi_production.money_counter_app.interactor.BalanceInteractor
import com.example.holmi_production.money_counter_app.interactor.CategoryInteractor
import com.example.holmi_production.money_counter_app.interactor.NotificationInteractor
import com.example.holmi_production.money_counter_app.interactor.SpendingInteractor
import com.example.holmi_production.money_counter_app.notification.NotificationManager
import com.example.holmi_production.money_counter_app.storage.*
import dagger.Module
import dagger.Provides

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
@Module
object InteractorModule {

    @Provides
    @PerFeature
    fun provideBalanceInteractor(
        spendingInteractor: SpendingInteractor,
        balanceRepository: BalanceRepository,
        periodsRepository: PeriodsRepository
    ): BalanceInteractor {
        return BalanceInteractor(
            spendingInteractor,
            balanceRepository,
            periodsRepository
        )
    }

    @Provides
    @PerFeature
    fun provideCategoryInteractor(
        categoryRepository: CategoryRepository,
        subCategoryRepository: SubCategoryRepository
    ): CategoryInteractor {
        return CategoryInteractor(
            categoryRepository,
            subCategoryRepository
        )
    }

    @Provides
    @PerFeature
    fun provideNotificationInteractor(
        sumPerDayRepository: SumPerDayRepository,
        notificationManager: NotificationManager
    ): NotificationInteractor {
        return NotificationInteractor(
            sumPerDayRepository,
            notificationManager
        )
    }

    @Provides
    @PerFeature
    fun provideSpendingInteractor(
        spendingRepository: SpendingRepository,
        sumPerDayRepository: SumPerDayRepository,
        settingRepository: SettingRepository,
        periodsRepository: PeriodsRepository,
        categoryInteractor: CategoryInteractor
    ): SpendingInteractor {
        return SpendingInteractor(
            spendingRepository,
            sumPerDayRepository,
            settingRepository,
            periodsRepository,
            categoryInteractor
        )
    }

}