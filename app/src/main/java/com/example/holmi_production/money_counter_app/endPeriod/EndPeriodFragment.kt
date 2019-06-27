package com.example.holmi_production.money_counter_app.endPeriod

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment

class EndPeriodFragment : AndroidXMvpAppCompatFragment() {


    @InjectPresenter
    lateinit var presenter: EndPeriodPresenter

    @ProvidePresenter
    fun initPresenter(): EndPeriodPresenter {
        return App.component.getEndPeriodPresenter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getDatas()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_end_period, container, false)
    }
}