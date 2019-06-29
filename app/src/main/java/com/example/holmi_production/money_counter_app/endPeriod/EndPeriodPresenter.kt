package com.example.holmi_production.money_counter_app.endPeriod

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import javax.inject.Inject
@InjectViewState
class EndPeriodPresenter @Inject constructor(): MvpPresenter<EndPeriodView>() {

    fun getDatas(){

    }

    fun goToMain(){
        viewState.goToMain()
    }

}