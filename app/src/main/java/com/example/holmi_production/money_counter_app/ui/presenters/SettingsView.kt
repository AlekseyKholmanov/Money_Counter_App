package com.example.holmi_production.money_counter_app.ui.presenters

import moxy.MvpView

interface SettingsView:MvpView {
    fun showMessage(message:String)
    fun showMessage(resId:Int)
    fun updateEndMonth(day: Int)

}
