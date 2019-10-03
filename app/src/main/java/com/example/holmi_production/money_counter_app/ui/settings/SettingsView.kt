package com.example.holmi_production.money_counter_app.ui.settings

import com.arellomobile.mvp.MvpView

interface SettingsView:MvpView {
    fun showMessage(message:String)
    fun showMessage(resId:Int)

}
