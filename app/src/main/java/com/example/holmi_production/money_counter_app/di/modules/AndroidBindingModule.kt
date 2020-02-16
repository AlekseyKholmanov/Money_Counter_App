package com.example.holmi_production.money_counter_app.di.modules

import com.example.holmi_production.money_counter_app.di.ActivityScoped
import com.example.holmi_production.money_counter_app.main.MainActivity
import com.example.holmi_production.money_counter_app.ui.charts_fragments.balance.BalanceChartFragment
import com.example.holmi_production.money_counter_app.ui.charts_fragments.bar.StackedChartFragment
import com.example.holmi_production.money_counter_app.ui.charts_fragments.pie.PieChartFragment
import com.example.holmi_production.money_counter_app.ui.costs_fragment.CostsFragment
import com.example.holmi_production.money_counter_app.ui.keyboard_fragment.KeyboardFragment
import com.example.holmi_production.money_counter_app.ui.keyboard_fragment.KeyboardPartFragment
import com.example.holmi_production.money_counter_app.ui.keyboard_fragment.category_picker_fragment.CategoryPickerFragment
import com.example.holmi_production.money_counter_app.ui.limits_fragment.LimitsFragment
import com.example.holmi_production.money_counter_app.ui.settings.SettingsFragment
import com.example.holmi_production.money_counter_app.ui.topbar_fragment.TopbarFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AndroidBindingModule{
    @ActivityScoped
    @ContributesAndroidInjector
    abstract fun mainactivity():MainActivity

    /*Fragments*/

    @ActivityScoped
    @ContributesAndroidInjector
    abstract fun topbarFarment():TopbarFragment

    @ContributesAndroidInjector
    abstract fun settingsFragment():SettingsFragment

    @ContributesAndroidInjector
    abstract fun limitsFragment():LimitsFragment

    @ContributesAndroidInjector
    abstract fun keyboardFragment():KeyboardFragment

    @ContributesAndroidInjector
    abstract fun keybordFragmentPart():KeyboardPartFragment

    @ContributesAndroidInjector
    abstract fun categoryPickerFragment():CategoryPickerFragment

    @ContributesAndroidInjector
    abstract fun costsFarment():CostsFragment

    @ContributesAndroidInjector
    abstract fun pieChartFragment():PieChartFragment

    @ContributesAndroidInjector
    abstract fun barChartFragment():StackedChartFragment

    @ContributesAndroidInjector
    abstract fun balanceChartFragment():BalanceChartFragment
}