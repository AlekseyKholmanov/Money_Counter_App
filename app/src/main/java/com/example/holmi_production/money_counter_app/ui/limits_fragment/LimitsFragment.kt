package com.example.holmi_production.money_counter_app.ui.limits_fragment

import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment

class LimitsFragment: AndroidXMvpAppCompatFragment(), LimitsView {

    companion object{
        fun newInstance(): LimitsFragment {
            return LimitsFragment()
        }
    }
}