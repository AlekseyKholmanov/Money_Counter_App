package com.example.holmi_production.money_counter_app.main

import com.arellomobile.mvp.InjectViewState
import com.example.holmi_production.money_counter_app.interactor.NotificationInteractor
import com.example.holmi_production.money_counter_app.mvp.BasePresenter
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(
    private val notificationInteractor: NotificationInteractor) : BasePresenter<MainView>() {

    fun getNotification() {
        notificationInteractor.alarmTriggered().keep()
    }
}