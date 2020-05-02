package com.example.holmi_production.money_counter_app.ui.fragments

import com.example.holmi_production.money_counter_app.ui.presenters.LimitsView
import moxy.MvpAppCompatFragment

class LimitsFragment: MvpAppCompatFragment(),
    LimitsView {

    companion object{
        fun newInstance(): LimitsFragment {
            return LimitsFragment()
        }
    }
}