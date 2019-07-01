package com.example.holmi_production.money_counter_app.di.components

import com.example.holmi_production.money_counter_app.main.MainActivity
import com.example.holmi_production.money_counter_app.chart.PieChartPresenter
import com.example.holmi_production.money_counter_app.costs.CostsPresenter
import com.example.holmi_production.money_counter_app.di.modules.ApplicationModule
import com.example.holmi_production.money_counter_app.di.modules.ContextModule
import com.example.holmi_production.money_counter_app.di.modules.PreferenceModule
import com.example.holmi_production.money_counter_app.endPeriod.EndPeriodPresenter
import com.example.holmi_production.money_counter_app.firstLaunch.FirstLaunchPresenter
import com.example.holmi_production.money_counter_app.keyboard.Keyboard
import com.example.holmi_production.money_counter_app.keyboard.KeyboardFragmentPresenter
import com.example.holmi_production.money_counter_app.main.MainPresenter
import com.example.holmi_production.money_counter_app.notification.NotificationAlarmReciever
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ContextModule::class, ApplicationModule::class, PreferenceModule::class])
interface ApplicationComponent {
    fun getKeyboardPresenter(): KeyboardFragmentPresenter
    fun getCostsPresenter(): CostsPresenter
    fun getFirstLaunchPresenter(): FirstLaunchPresenter
    fun getChartPresenter(): PieChartPresenter
    fun getMainPresenter():MainPresenter
    fun getEndPeriodPresenter():EndPeriodPresenter
    fun inject(activity: MainActivity)
    fun inject(notificationAlarmReciever: NotificationAlarmReciever)
    fun inject(keyboard: Keyboard)
}