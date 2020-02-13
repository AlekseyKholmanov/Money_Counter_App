package com.example.holmi_production.money_counter_app.ui.charts_fragments.bar

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.extensions.toRUformat
import com.example.holmi_production.money_counter_app.model.CategoryType
import com.example.holmi_production.money_counter_app.model.entity.Spending
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.chart_bar.*
import leakcanary.AppWatcher
import org.joda.time.DateTime
import kotlin.collections.ArrayList

class StackedChartFragment : AndroidXMvpAppCompatFragment(),
    StackedView {
    companion object {
        fun newInstance(): StackedChartFragment {
            return StackedChartFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.chart_bar, container, false)
    }

    @ProvidePresenter
    fun providePresenter() = App.component.getStackedPresenter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("M_PieChartFragment", "pie bar created")
        presenter.observeDatas()
        chart = view.findViewById(R.id.chart_bar)
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
    }

    override fun onDestroy() {
        super.onDestroy()
        AppWatcher.objectWatcher.watch(this)
    }

    override fun showError() {
        showEmptyPlaceholder()
    }

    override fun showGraph(list: Map<DateTime, List<Spending>>) {
        if (list.isEmpty()) {
            showEmptyPlaceholder()
        } else {
            hidePlaceholder()
            val keys = list.keys.toTypedArray()

            val dataSets = ArrayList<IBarDataSet>()
            val labels = ArrayList<String>()
            for (i in 0 until list.count()) {
                val groupedSpending = list.getValue(keys[i]).groupBy { CategoryType.values()[it.categoryId] }
                val sums = ArrayList<Pair<Float, CategoryType>>()
                groupedSpending.forEach { (type, spendings) ->
                    sums.add(Pair(spendings.sumByDouble { it.sum }.toFloat(), type))

                    val entry = BarEntry(keys[i].dayOfYear().get().toFloat(), sums.map { it.first }.toFloatArray())
                    val barDataSet = BarDataSet(listOf(entry), i.toString())

                    barDataSet.colors = sums.map { it.second.color }
                    dataSets.add(barDataSet)
                    labels.add(keys[i].toRUformat())
                }
                val data = BarData(dataSets)

                chart.data = data
                chart.barData.setValueTextSize(18f)

                chart.xAxis.labelCount = list.count()
                chart.animateY(2000, Easing.EaseInOutBack)
                chart.invalidate()
            }
        }
    }

    private fun showEmptyPlaceholder() {
        emptyPlaceholder_bar.isVisible = true
        chart_bar.isVisible = false
    }

    private fun hidePlaceholder() {
        emptyPlaceholder_bar.isVisible = false
        chart_bar.isVisible = true
    }

    @InjectPresenter
    lateinit var presenter: StackedPresenter

    lateinit var chart: BarChart

}