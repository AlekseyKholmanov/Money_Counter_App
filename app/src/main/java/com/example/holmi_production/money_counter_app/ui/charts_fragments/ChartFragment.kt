package com.example.holmi_production.money_counter_app.ui.charts_fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment
import kotlinx.android.synthetic.main.fragment_bottom_chart.*

class ChartFragment : AndroidXMvpAppCompatFragment() {

    companion object{
        fun newInstance(): ChartFragment {
            return ChartFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_bottom_chart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chart_tabs.setupWithViewPager(chart_viewPager)
        chart_viewPager.adapter = ChartFragmentAdapter(childFragmentManager)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("qwerty", "chart attached")
    }

    override fun onStop() {
        super.onStop()
        Log.d("qwerty", "chart stopped")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("qwerty", "chart view destroyed")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("qwerty", "chart detached")
    }

    override fun onResume() {
        super.onResume()
        Log.d("qwerty", "chart resumed")
    }

}