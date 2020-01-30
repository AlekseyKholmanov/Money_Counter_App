package com.example.holmi_production.money_counter_app.di.modules

import com.example.holmi_production.money_counter_app.ui.charts_fragments.balance.BalanceChartFragment
import com.example.holmi_production.money_counter_app.ui.charts_fragments.bar.StackedChartFragment
import com.example.holmi_production.money_counter_app.ui.charts_fragments.pie.PieChartFragment
import com.example.holmi_production.money_counter_app.ui.costs_fragment.CostsFragment
import com.example.holmi_production.money_counter_app.ui.keyboard_fragment.KeyboardFragment
import com.example.holmi_production.money_counter_app.ui.keyboard_fragment.category_picker_fragment.create_category_dialog.CategoryDetailFragment
import com.example.holmi_production.money_counter_app.ui.keyboard_fragment.category_picker_fragment.create_category_dialog.DialogFragmentTabContainer
import com.example.holmi_production.money_counter_app.ui.limits_fragment.LimitsFragment
import com.example.holmi_production.money_counter_app.ui.topbar_fragment.TopbarFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributeKeyboardFragment(): KeyboardFragment

    @ContributesAndroidInjector
    abstract fun contributeTopbarFragment(): TopbarFragment

    @ContributesAndroidInjector
    abstract fun contributeLimitsFragment(): LimitsFragment

    @ContributesAndroidInjector
    abstract fun contributeDialogFragmentTabContainer(): DialogFragmentTabContainer

    @ContributesAndroidInjector
    abstract fun contributeCostsFragment(): CostsFragment

    @ContributesAndroidInjector
    abstract fun contributePieChartFragment(): PieChartFragment

    @ContributesAndroidInjector
    abstract fun contributeBalanceChartFragment(): BalanceChartFragment

    @ContributesAndroidInjector
    abstract fun contributeStackedChartFragment(): StackedChartFragment
}

