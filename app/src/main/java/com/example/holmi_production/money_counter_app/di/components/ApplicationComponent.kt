package com.example.holmi_production.money_counter_app.di.components

import com.example.holmi_production.money_counter_app.MainActivity
import com.example.holmi_production.money_counter_app.NotificationManager
import com.example.holmi_production.money_counter_app.costs.CostsPresenter
import com.example.holmi_production.money_counter_app.di.modules.ApplicationModule
import com.example.holmi_production.money_counter_app.di.modules.ContextModule
import com.example.holmi_production.money_counter_app.di.modules.PreferenceModule
import com.example.holmi_production.money_counter_app.firstLaunch.FirstLaunchPresenter
import com.example.holmi_production.money_counter_app.main.MainFragment
import com.example.holmi_production.money_counter_app.main.MainFragmentPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ContextModule::class, ApplicationModule::class, PreferenceModule::class])
interface ApplicationComponent {
    fun getMainPresenter(): MainFragmentPresenter
    fun getCostsPresenter(): CostsPresenter
    fun getFirstLaunchPresenter(): FirstLaunchPresenter
}