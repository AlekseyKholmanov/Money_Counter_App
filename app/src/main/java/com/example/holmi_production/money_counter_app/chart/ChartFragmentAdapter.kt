package com.example.holmi_production.money_counter_app.chart

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.holmi_production.money_counter_app.chart.bar.StackedChartFragment
import com.example.holmi_production.money_counter_app.chart.pie.PieChartFragment

class ChartFragmentAdapter(fm: FragmentManager, behavior: Int = BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) :
    FragmentPagerAdapter(fm, behavior) {
    override fun getItem(position: Int): Fragment {
        return when (ChartType.values()[position]){
            ChartType.PIE_CHART -> PieChartFragment.newInstance()
            ChartType.GRAPH_CHART -> StackedChartFragment.newInstance()
        }
    }

    override fun getCount(): Int = ChartType.values().count()

    override fun getPageTitle(position: Int): CharSequence {
        return when (ChartType.values()[position]) {
            ChartType.PIE_CHART -> "Pie"
            ChartType.GRAPH_CHART -> "Stacked bar"
        }
    }
}