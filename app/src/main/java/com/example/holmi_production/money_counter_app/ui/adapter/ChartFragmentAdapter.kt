package com.example.holmi_production.money_counter_app.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.holmi_production.money_counter_app.model.ChartType
import com.example.holmi_production.money_counter_app.ui.fragments.charts.ChartStackedFragment
import com.example.holmi_production.money_counter_app.ui.fragments.charts.ChartPieFragment
import com.example.holmi_production.money_counter_app.ui.fragments.charts.ChartBalanceFragment

class ChartFragmentAdapter(fm: FragmentManager, behavior: Int = BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) :
    FragmentPagerAdapter(fm, behavior) {
    override fun getItem(position: Int): Fragment {
        return when (ChartType.values()[position]){
            ChartType.PIE_CHART -> ChartPieFragment.newInstance()
//            ChartType.GRAPH_CHART -> ChartStackedFragment.newInstance()
            ChartType.BALANCE_CHART -> ChartBalanceFragment.newInstance()
        }
    }

    override fun getCount(): Int = ChartType.values().count()

    override fun getPageTitle(position: Int): CharSequence {
        return when (ChartType.values()[position]) {
            ChartType.PIE_CHART -> "Pie"
//            ChartType.GRAPH_CHART -> "Stacked bar"
            ChartType.BALANCE_CHART -> "Balance chart"
        }
    }
}