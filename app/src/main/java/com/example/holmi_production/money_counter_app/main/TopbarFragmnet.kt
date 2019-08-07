package com.example.holmi_production.money_counter_app.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.extensions.toDateTime
import com.example.holmi_production.money_counter_app.extensions.toRUformat
import com.example.holmi_production.money_counter_app.model.FilterPeriods
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment
import com.example.holmi_production.money_counter_app.storage.PeriodsRepository
import com.example.holmi_production.money_counter_app.storage.SettingRepository
import kotlinx.android.synthetic.main.topbar.*
import org.joda.time.DateTime
import org.joda.time.Duration
import javax.inject.Inject

class TopbarFragmnet : AndroidXMvpAppCompatFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.topbar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        topbar_left.setOnClickListener {
            periodChanged(false)
        }
        topbar_right.setOnClickListener {
            periodChanged(true)
        }
        topbar_date.setOnClickListener {
            mTopbarListener?.chooseDatePressed()
        }
        App.component.inject(this)
        initializeDate()
    }
    private fun initializeDate() {
        val (start, end) = settingRepository.getTopbarDate()
        val text = "${start.toDateTime().toRUformat()} - ${end.toDateTime().toRUformat()}"
        topbar_date.text = text
    }

    private fun periodChanged(isToFuture: Boolean) {
        val (start, end) = settingRepository.getTopbarDate()
        val difDays = if (isToFuture)
            Duration(start, end).standardDays + 1 //чтобы не пересекались даты
        else
            Duration(end, start).standardDays - 1 //чтобы не пересекались даты
        val newDate =
            Pair(
                start.toDateTime().plusDays(difDays.toInt()),
                end.toDateTime().plusDays(difDays.toInt())
            )
        saveDate(newDate.first, newDate.second)
        val newText = "${newDate.first.toDateTime().toRUformat()} - ${newDate.second.toDateTime().toRUformat()}"
        topbar_date.text = newText
        mTopbarListener?.rightPressed(newDate.first, newDate.second)
    }

    private fun saveDate(start: DateTime, end: DateTime) {
        periodsRepository.insert(FilterPeriods("", start, end))
            .subscribe()
    }

    @Inject
    lateinit var settingRepository: SettingRepository

    @Inject
    lateinit var periodsRepository: PeriodsRepository

    var mTopbarListener: ITopbarListener? = null

    fun setListener(mTopbarListener: ITopbarListener?) {
        this.mTopbarListener = mTopbarListener
    }
}

interface ITopbarListener {
    fun leftPressed(start: DateTime, end: DateTime)
    fun rightPressed(start: DateTime, end: DateTime)
    fun chooseDatePressed()
}