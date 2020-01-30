package com.example.holmi_production.money_counter_app.di.modules

import com.example.holmi_production.money_counter_app.main.MainActivity
import com.example.holmi_production.money_counter_app.notification.NotificationAlarmReciever
import com.example.holmi_production.money_counter_app.ui.charts_fragments.balance.BalancePresenter
import com.example.holmi_production.money_counter_app.ui.charts_fragments.bar.StackedPresenter
import com.example.holmi_production.money_counter_app.ui.charts_fragments.pie.PieChartPresenter
import com.example.holmi_production.money_counter_app.ui.costs_fragment.CostsPresenter
import com.example.holmi_production.money_counter_app.ui.end_period_fragment.EndPeriodPresenter
import com.example.holmi_production.money_counter_app.ui.first_launch_fragment.FirstLaunchPresenter
import com.example.holmi_production.money_counter_app.ui.keyboard_fragment.KeyboardPartFragment
import com.example.holmi_production.money_counter_app.ui.keyboard_fragment.KeyboardPresenter
import com.example.holmi_production.money_counter_app.ui.keyboard_fragment.category_picker_fragment.CategoryPickerPresenter
import com.example.holmi_production.money_counter_app.ui.keyboard_fragment.category_picker_fragment.create_category_dialog.PresenterCreateCategory
import com.example.holmi_production.money_counter_app.ui.settings.SettingsPresenter
import com.example.holmi_production.money_counter_app.ui.topbar_fragment.TopbarPresenter
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BindingModule {
    @ContributesAndroidInjector
    abstract fun getKeyboardPresenter(): KeyboardPresenter

    @ContributesAndroidInjector
    abstract fun getCostsPresenter(): CostsPresenter

    @ContributesAndroidInjector
    abstract fun getSettingsPresenter(): SettingsPresenter

    @ContributesAndroidInjector
    abstract fun getFirstLaunchPresenter(): FirstLaunchPresenter

    @ContributesAndroidInjector
    abstract fun getChartPresenter(): PieChartPresenter

    @ContributesAndroidInjector
    abstract fun getEndPeriodPresenter(): EndPeriodPresenter

    @ContributesAndroidInjector
    abstract fun getStackedPresenter(): StackedPresenter

    @ContributesAndroidInjector
    abstract fun getBalancePresenter(): BalancePresenter

    @ContributesAndroidInjector
    abstract fun getTopbarPresenter(): TopbarPresenter

    @ContributesAndroidInjector
    abstract fun getCategoryPickerPresenter(): CategoryPickerPresenter

    @ContributesAndroidInjector
    @ActivityScoped
    abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector
    @ActivityScoped
    abstract fun getAlarmReciever(): NotificationAlarmReciever

    @ContributesAndroidInjector
    abstract fun getKeybortPart(): KeyboardPartFragment

    @ContributesAndroidInjector
    abstract fun getCategoryCreatePresenter(): PresenterCreateCategory

}