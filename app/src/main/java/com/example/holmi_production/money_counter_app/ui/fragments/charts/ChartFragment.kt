package com.example.holmi_production.money_counter_app.ui.fragments.charts

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.di.components.AppComponent
import com.example.holmi_production.money_counter_app.main.BaseFragment
import com.example.holmi_production.money_counter_app.ui.adapter.ChartFragmentAdapter
import com.example.holmi_production.money_counter_app.ui.presenters.charts.ChartPiePresenter
import kotlinx.android.synthetic.main.fragment_bottom_chart.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class ChartFragment : BaseFragment(R.layout.fragment_bottom_chart) {

    companion object{
        fun newInstance(): ChartFragment {
            return ChartFragment()
        }
    }
    override fun inject()= Unit


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