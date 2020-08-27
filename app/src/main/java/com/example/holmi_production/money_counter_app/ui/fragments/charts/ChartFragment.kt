package com.example.holmi_production.money_counter_app.ui.fragments.charts

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.main.BaseFragment
import com.example.holmi_production.money_counter_app.ui.adapter.ChartFragmentAdapter
import kotlinx.android.synthetic.main.fragment_bottom_chart.*

class ChartFragment : BaseFragment(R.layout.fragment_bottom_chart) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chart_tabs.setupWithViewPager(chart_viewPager)
        chart_viewPager.adapter =
            ChartFragmentAdapter(
                childFragmentManager
            )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("qwerty", "chart attached")
    }

}