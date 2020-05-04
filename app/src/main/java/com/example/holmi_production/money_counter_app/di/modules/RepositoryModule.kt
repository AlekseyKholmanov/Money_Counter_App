package com.example.holmi_production.money_counter_app.di.modules

import android.content.SharedPreferences
import com.example.holmi_production.money_counter_app.di.common.PerFeature
import com.example.holmi_production.money_counter_app.orm.ExpenseDatabase
import com.example.holmi_production.money_counter_app.storage.*
import com.f2prateek.rx.preferences2.RxSharedPreferences
import dagger.Module
import dagger.Provides

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
@Module
object RepositoryModule {

    @Provides
    @PerFeature
    fun provideBalanceRepository(
        db: ExpenseDatabase
    ): BalanceRepository {
        return BalanceRepository(db)
    }

    @Provides
    @PerFeature
    fun provideCategoryRepository(
        db: ExpenseDatabase
    ): CategoryRepository {
        return CategoryRepository(db)
    }

    @Provides
    @PerFeature
    fun providePeriodsRepository(
        db: ExpenseDatabase
    ): PeriodsRepository {
        return PeriodsRepository(db)
    }

    @Provides
    @PerFeature
    fun provideSettingRepository(
        prefs: SharedPreferences,
        rxPrefs: RxSharedPreferences
    ): SettingRepository {
        return SettingRepository(prefs, rxPrefs)
    }

    @Provides
    @PerFeature
    fun provideSpendingRepository(
        db: ExpenseDatabase
    ): SpendingRepository {
        return SpendingRepository(db)
    }

    @Provides
    @PerFeature
    fun provideSubCategoryRepository(
        db: ExpenseDatabase
    ): SubCategoryRepository {
        return SubCategoryRepository(db)
    }

    @Provides
    @PerFeature
    fun provideSumPerDayRepository(
        db: ExpenseDatabase
    ): SumPerDayRepository {
        return SumPerDayRepository(db)
    }
}