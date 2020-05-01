package com.example.holmi_production.money_counter_app.di.components

import com.example.holmi_production.money_counter_app.di.modules.*
import com.example.holmi_production.money_counter_app.main.MainActivity
import com.example.holmi_production.money_counter_app.notification.NotificationAlarmReciever
import com.example.holmi_production.money_counter_app.ui.presenters.charts.ChartBalancePresenter
import com.example.holmi_production.money_counter_app.ui.presenters.charts.ChartStackedPresenter
import com.example.holmi_production.money_counter_app.ui.presenters.charts.ChartPiePresenter
import com.example.holmi_production.money_counter_app.ui.fragments.KeyboardPartFragment
import com.example.holmi_production.money_counter_app.ui.presenters.*
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
    fun getCreateCategoryPresenter(): CreateCategoryPresenter
    fun getSettingsPresenter(): SettingsPresenter
    fun getFirstLaunchPresenter(): FirstLaunchPresenter
    fun getChartPresenter(): ChartPiePresenter
    fun getEndPeriodPresenter(): EndPeriodPresenter
    fun getStackedPresenter(): ChartStackedPresenter
    fun getBalancePresenter(): ChartBalancePresenter
    fun getTopbarPresenter(): TopbarPresenter
    fun getCategoryPickerPresenter(): CategoryPickerPresenter
    fun getEditCategoryPresenter(): EditCategoryPresenter
    fun inject(activity: MainActivity)
    fun inject(notificationAlarmReciever: NotificationAlarmReciever)
    fun inject(keyboardFr: KeyboardPartFragment)
}
