package com.example.holmi_production.money_counter_app.chart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.extensions.toRUformat
import com.example.holmi_production.money_counter_app.model.CategoryType
import com.example.holmi_production.money_counter_app.model.Spending
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import org.joda.time.DateTime
import kotlin.collections.ArrayList

class StackedChartFragmnet : AndroidXMvpAppCompatFragment(), StackedView {

    lateinit var chart: BarChart

    override fun showFraph(list: Map<DateTime, List<Spending>>) {
        val keys = list.keys.toTypedArray()

        val dataSets = ArrayList<IBarDataSet>()
        val labels = ArrayList<String>()
        for (i in 0 until list.count()) {
            val groupedSpending = list.getValue(keys[i]).groupBy { it.categoryTypes }
            val sums = ArrayList<Pair<Float, CategoryType>>()
            groupedSpending.forEach { (type, spendings) ->
                sums.add(Pair(spendings.sumByDouble { it.sum }.toFloat(), type))

                val entry = BarEntry(keys[i].dayOfYear().get().toFloat(), sums.map { it.first }.toFloatArray())
                val barDataSet = BarDataSet(listOf(entry), i.toString())
                barDataSet.valueFormatter = MyValueFormater()

                barDataSet.colors = sums.map { it.second.color }
                dataSets.add(barDataSet)
                labels.add(keys[i].toRUformat())
            }
            val data = BarData(dataSets)

            chart.data = data
            chart.barData.setValueTextSize(18f)

            chart.xAxis.labelCount = list.count()
            chart.notifyDataSetChanged()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.chart_graph, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chart = view.findViewById(R.id.barChart)
        chart.setPinchZoom(false)

        chart.setDrawGridBackground(false)
        chart.setDrawBarShadow(false)

        chart.setDrawValueAboveBar(false)
        chart.isHighlightFullBarEnabled = false

        chart.description.text = ""

        val leftAxis = chart.axisLeft
        leftAxis.axisMinimum = 0f // this replaces setStartAtZero(true)
        chart.axisRight.isEnabled = false

        val xLabels = chart.xAxis
        xLabels.position = XAxis.XAxisPosition.BOTTOM
        xLabels.setDrawGridLines(false)
        xLabels.textSize = 10f

        val l = chart.legend
        l.isEnabled = false
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)
        l.formSize = 20f
        l.formToTextSpace = 4f
        l.xEntrySpace = 6f
        l.textSize = 25f

        presenter.getDatas()

    }

    @InjectPresenter
    lateinit var presenter: StackedPresenter

    @ProvidePresenter
    fun providePresenter() = App.component.getStackedPresenter()

}