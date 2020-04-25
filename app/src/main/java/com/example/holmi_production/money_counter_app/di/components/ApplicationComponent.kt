package com.example.holmi_production.money_counter_app.di.components

import com.example.holmi_production.money_counter_app.di.modules.*
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
import com.example.holmi_production.money_counter_app.ui.presenters.CategoryPickerPresenter
import com.example.holmi_production.money_counter_app.ui.keyboard_fragment.category_picker_fragment.create_category_dialog.PresenterCreateCategory
import com.example.holmi_production.money_counter_app.ui.settings.SettingsPresenter
import com.example.holmi_production.money_counter_app.ui.topbar_fragment.TopbarPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        ContextModule::class,
        DatabaseModule::class,
        PreferenceModule::class,
        WorkerModule::class]
)
interface ApplicationComponent {

    fun getKeyboardPresenter(): KeyboardPresenter
    fun getCostsPresenter(): CostsPresenter
    fun getSettingsPresenter(): SettingsPresenter
    fun getFirstLaunchPresenter(): FirstLaunchPresenter
    fun getChartPresenter(): PieChartPresenter
    fun getEndPeriodPresenter(): EndPeriodPresenter
    fun getStackedPresenter(): StackedPresenter
    fun getBalancePresenter(): BalancePresenter
    fun getTopbarPresenter(): TopbarPresenter
    fun getCategoryPickerPresenter(): CategoryPickerPresenter
    fun inject(activity: MainActivity)
    fun inject(notificationAlarmReciever: NotificationAlarmReciever)
    fun inject(keyboardFr: KeyboardPartFragment)
    fun getCategoryCreatePresenter(): PresenterCreateCategory
}
