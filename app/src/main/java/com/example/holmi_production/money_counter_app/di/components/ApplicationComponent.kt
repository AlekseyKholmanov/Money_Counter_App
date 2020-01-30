package com.example.holmi_production.money_counter_app.di.components

import android.app.Application
import com.example.holmi_production.money_counter_app.App
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
import com.example.holmi_production.money_counter_app.ui.keyboard_fragment.category_picker_fragment.CategoryPickerPresenter
import com.example.holmi_production.money_counter_app.ui.keyboard_fragment.category_picker_fragment.create_category_dialog.PresenterCreateCategory
import com.example.holmi_production.money_counter_app.ui.settings.SettingsPresenter
import com.example.holmi_production.money_counter_app.ui.topbar_fragment.TopbarPresenter
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ApplicationModule::class,
        ApplicationModule2::class,
        DatabaseModule::class,
        PreferenceModule::class,
        BindingModule::class,
        WorkerModule::class,
        MainActivityModule::class]
)
interface ApplicationComponent:AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}

