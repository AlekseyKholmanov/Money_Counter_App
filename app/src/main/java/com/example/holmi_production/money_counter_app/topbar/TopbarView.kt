package com.example.holmi_production.money_counter_app.topbar

import com.arellomobile.mvp.MvpView
import org.joda.time.DateTime

interface TopbarView : MvpView {
    fun showDate(leftBorder: DateTime, rightBorder: DateTime)
}