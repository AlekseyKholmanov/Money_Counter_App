package com.example.holmi_production.money_counter_app.chart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.R
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
import java.util.*

class StackedChartFragmnet : AndroidXMvpAppCompatFragment(), StackedView {

    lateinit var chart: BarChart

    override fun showFraph(list: Map<DateTime, List<Spending>>) {
        val keys = list.keys.toTypedArray()
        val values = ArrayList<BarEntry>()
        for (i in 0 until list.count()) {
            val sum = list[keys[i]]?.sumByDouble { it.sum }?.toFloat() ?: 0f
            values.add(BarEntry(i.toFloat(), sum))
        }

        val set = BarDataSet(values, "blabla")
        val dataSets = ArrayList<IBarDataSet>()
        dataSets.add(set)
        val data = BarData(dataSets)
        chart.data = data
        chart.notifyDataSetChanged()

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

        val leftAxis = chart.axisLeft
        leftAxis.axisMinimum = 0f // this replaces setStartAtZero(true)
        chart.axisRight.isEnabled = false

        val xLabels = chart.xAxis
        xLabels.position = XAxis.XAxisPosition.TOP

        val l = chart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)
        l.formSize = 8f
        l.formToTextSpace = 4f
        l.xEntrySpace = 6f

        presenter.getDatas()

    }

    @InjectPresenter
    lateinit var presenter: StackedPresenter

    @ProvidePresenter
    fun providePresenter() = App.component.getStackedPresenter()

}