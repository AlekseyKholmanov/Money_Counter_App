package com.example.holmi_production.money_counter_app.ui.fragments

import android.os.Bundle
import android.view.View
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.main.BaseFragment


class LimitsFragment: BaseFragment(R.layout.fragment_limits){


    override fun inject() {
   //AppComponent.instance.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
    }
}