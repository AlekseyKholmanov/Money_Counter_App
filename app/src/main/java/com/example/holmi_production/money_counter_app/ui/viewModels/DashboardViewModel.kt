package com.example.holmi_production.money_counter_app.ui.viewModels

import com.example.holmi_production.money_counter_app.storage.SettingRepository
import com.example.holmi_production.money_counter_app.storage.impl.TransactionDatabaseImpl
import com.example.holmi_production.money_counter_app.storage.impl.SumPerDayDatabaseImpl

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
class DashboardViewModel(
    private val sumPerDayDatabase: SumPerDayDatabaseImpl,
    private val spendingDatabase: TransactionDatabaseImpl,
    private val settingRepository: SettingRepository
) {

    fun observeSumPerDay(){

    }
}