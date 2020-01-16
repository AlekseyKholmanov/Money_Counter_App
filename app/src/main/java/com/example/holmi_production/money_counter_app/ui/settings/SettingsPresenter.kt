package com.example.holmi_production.money_counter_app.ui.settings

import com.arellomobile.mvp.InjectViewState
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.interactor.CategoryInteractor
import com.example.holmi_production.money_counter_app.interactor.SpendingInteractor
import com.example.holmi_production.money_counter_app.mvp.BasePresenter
import com.example.holmi_production.money_counter_app.storage.SettingRepository
import javax.inject.Inject

@InjectViewState
class SettingsPresenter @Inject constructor(
    private val spendingInteractor: SpendingInteractor,
    private val categoryInteractor: CategoryInteractor,
    private val settingRepository: SettingRepository) :
    BasePresenter<SettingsView>() {
    fun deleteData() {
        spendingInteractor.deleteAll()
            .subscribe {
                viewState.showMessage(R.string.settings_message_clear_success)
            }
            .keep()

    }

    fun deleteCategory() {
        categoryInteractor.deleteAll()
            .subscribe {
                viewState.showMessage(R.string.settings_message_clear_success)
            }
            .keep()
    }

    fun saveEndMonth(day:Int){
        settingRepository.setEndMonth(day)
        viewState.updateEndMonth(day)
    }

    fun getEndMonth(){
        val day = settingRepository.getEndMonth()
        viewState.updateEndMonth(day)
    }
}