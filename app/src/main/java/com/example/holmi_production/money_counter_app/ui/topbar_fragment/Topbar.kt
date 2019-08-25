package com.example.holmi_production.money_counter_app.ui.topbar_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.extensions.toRUformat
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment
import kotlinx.android.synthetic.main.topbar.*
import leakcanary.AppWatcher
import org.joda.time.DateTime

class Topbar : AndroidXMvpAppCompatFragment(), TopbarView {
    override fun showDate(leftBorder: DateTime, rightBorder: DateTime) {
        topbar_date.text = " ${leftBorder.toRUformat()} - ${rightBorder.toRUformat()}"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.topbar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        topbar_left.setOnClickListener {
            presenter.setNewPeriod(false)
        }
        topbar_right.setOnClickListener {
            presenter.setNewPeriod(true)
        }
        topbar_date.setOnClickListener {
        }
        presenter.getPeriod()
    }

    override fun onDestroy() {
        super.onDestroy()
        AppWatcher.objectWatcher.watch(this)
    }

    @ProvidePresenter
    fun initPresenter(): TopbarPresenter {
        return App.component.getTopbarPresenter()
    }

    @InjectPresenter
    lateinit var presenter: TopbarPresenter
}