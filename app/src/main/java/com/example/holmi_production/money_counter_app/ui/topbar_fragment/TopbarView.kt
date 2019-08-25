package com.example.holmi_production.money_counter_app.ui.topbar_fragment

import com.arellomobile.mvp.MvpView
import org.joda.time.DateTime

interface TopbarView : MvpView {
    fun showDate(leftBorder: DateTime, rightBorder: DateTime)
}