package com.example.holmi_production.money_counter_app.ui.fragments

import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment
import com.example.holmi_production.money_counter_app.ui.presenters.LimitsView

class LimitsFragment: AndroidXMvpAppCompatFragment(),
    LimitsView {

    companion object{
        fun newInstance(): LimitsFragment {
            return LimitsFragment()
        }
    }
}