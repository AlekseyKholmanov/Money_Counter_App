package com.example.holmi_production.money_counter_app.ui.presenters


import com.example.holmi_production.money_counter_app.interactor.CategoryInteractor
import com.example.holmi_production.money_counter_app.interactor.SpendingInteractor
import com.example.holmi_production.money_counter_app.storage.AppPreference



class SettingsPresenter (
    private val spendingInteractor: SpendingInteractor,
    private val categoryInteractor: CategoryInteractor,
    private val appPreference: AppPreference) {
    fun deleteData() {
//        spendingInteractor.deleteAll()
//            .subscribe {
//                viewState.showMessage(R.string.settings_message_clear_success)
//            }
//            .keep()

    }

    fun deleteCategory() {
//        categoryInteractor.deleteAll()
//            .subscribe {
//                viewState.showMessage(R.string.settings_message_clear_success)
//            }
//            .keep()
    }

    fun saveEndMonth(day:Int){
//        settingRepository.setEndMonth(day)
//        viewState.updateEndMonth(day)
    }

    fun getEndMonth(){
//        val day = settingRepository.getEndMonth()
//        viewState.updateEndMonth(day)
    }
}