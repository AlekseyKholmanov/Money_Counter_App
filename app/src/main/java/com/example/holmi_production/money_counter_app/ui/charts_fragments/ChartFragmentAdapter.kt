package com.example.holmi_production.money_counter_app.ui.charts_fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.holmi_production.money_counter_app.ui.charts_fragments.bar.StackedChartFragment
import com.example.holmi_production.money_counter_app.ui.charts_fragments.pie.PieChartFragment
import com.example.holmi_production.money_counter_app.ui.charts_fragments.balance.BalanceChartFragment

class ChartFragmentAdapter(fm: FragmentManager, behavior: Int = BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) :
    FragmentPagerAdapter(fm, behavior) {
    override fun getItem(position: Int): Fragment {
        return when (ChartType.values()[position]){
            ChartType.PIE_CHART -> PieChartFragment.newInstance()
            ChartType.GRAPH_CHART -> StackedChartFragment.newInstance()
            ChartType.BALANCE_CHART -> BalanceChartFragment.newInstance()
        }
    }

    override fun getCount(): Int = ChartType.values().count()

    override fun getPageTitle(position: Int): CharSequence {
        return when (ChartType.values()[position]) {
            ChartType.PIE_CHART -> "Pie"
            ChartType.GRAPH_CHART -> "Stacked bar"
            ChartType.BALANCE_CHART -> "Balance chart"
        }
    }
}