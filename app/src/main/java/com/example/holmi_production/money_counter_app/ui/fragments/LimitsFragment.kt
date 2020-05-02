package com.example.holmi_production.money_counter_app.ui.fragments

import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.di.components.AppComponent
import com.example.holmi_production.money_counter_app.main.BaseFragment
import com.example.holmi_production.money_counter_app.ui.presenters.LimitsView
import moxy.MvpAppCompatFragment

class LimitsFragment: BaseFragment(R.layout.fragment_limits),
    LimitsView {

    companion object{
        fun newInstance(): LimitsFragment {
            return LimitsFragment()
        }
    }

    override fun inject() {
        AppComponent.instance.inject(this)
    }
}