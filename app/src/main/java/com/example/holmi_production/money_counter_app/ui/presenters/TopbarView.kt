package com.example.holmi_production.money_counter_app.ui.presenters

import moxy.MvpView
import org.joda.time.DateTime

interface TopbarView : MvpView {
    fun showDate(leftBorder: String)
}