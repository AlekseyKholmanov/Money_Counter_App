package com.example.holmi_production.money_counter_app.di.components

import com.example.holmi_production.money_counter_app.di.modules.ApplicationModule
import com.example.holmi_production.money_counter_app.di.modules.ContextModule
import com.example.holmi_production.money_counter_app.main.MainFragmentPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ContextModule::class, ApplicationModule::class])
interface ApplicationComponent {
    fun getMainPresenter():MainFragmentPresenter
}