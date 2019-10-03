package com.example.holmi_production.money_counter_app.ui.settings

import com.arellomobile.mvp.InjectViewState
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.interactor.SpendingInteractor
import com.example.holmi_production.money_counter_app.mvp.BasePresenter
import javax.inject.Inject

@InjectViewState
class SettingsPresenter @Inject constructor(private val spendingInteractor: SpendingInteractor) :
    BasePresenter<SettingsView>() {
    fun deleteData(){
        spendingInteractor.deleteAll()
            .subscribe {
                viewState.showMessage(R.string.settings_message_clear_success)
            }
            .keep()

    }
}