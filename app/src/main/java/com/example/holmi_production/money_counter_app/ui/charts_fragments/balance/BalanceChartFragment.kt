package com.example.holmi_production.money_counter_app.ui.charts_fragments.balance

import android.graphics.Color
import android.graphics.DashPathEffect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.entity.Balance
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment
import com.example.holmi_production.money_counter_app.ui.charts_fragments.bar.StackedChartFragment
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.Utils
import kotlinx.android.synthetic.main.chart_balance.*
import kotlinx.android.synthetic.main.chart_bar.emptyPlaceholder_bar
import leakcanary.AppWatcher
import java.util.ArrayList

class BalanceChartFragment : AndroidXMvpAppCompatFragment(), BalanceView {
    override fun showChart(balances: List<Balance>) {
        hidePlaceholder()
        val values = ArrayList<Entry>()
        for (i in 0 until balances.count()) {
            val value: Float = balances[i].amount.toFloat()
            values.add(Entry((balances[i].id.dayOfYear().get()).toFloat(), value))
        }
        if (chart.data != null && chart.data.dataSetCount > 0) {
            val dataSet: LineDataSet = chart.data.getDataSetByIndex(0) as LineDataSet
            dataSet.values = values
            dataSet.setDrawFilled(true)
            dataSet.notifyDataSetChanged()
            chart.data.notifyDataChanged()
            chart.notifyDataSetChanged()
        }
        else {
            val dataSet = LineDataSet(values, "LABEL")
            dataSet.setDrawIcons(false)
            dataSet.enableDashedLine(10f, 5f, 0f)
            dataSet.color = Color.BLACK
            dataSet.setCircleColor(Color.BLACK)
            dataSet.lineWidth = 1f
            dataSet.circleRadius = 2f
            dataSet.setDrawCircleHole(false)
            dataSet.formLineWidth = 1f
            dataSet.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
            dataSet.formSize = 15f

            // text size of values
            dataSet.valueTextSize = 9f

            if (Utils.getSDKInt() >= 18) {
                // drawables only supported on api level 18 and above
                val drawable = ContextCompat.getDrawable(context!!, R.drawable.fade_red)
                dataSet.fillDrawable = drawable
            } else {
                dataSet.fillColor = Color.BLACK
            }

            val dataSets = ArrayList<ILineDataSet>()
            dataSet.setDrawFilled(true)
            dataSets.add(dataSet) // add the data sets

            // create a data object with the data sets
            val data = LineData(dataSets)

            // set data
            chart.data = data
        }
        val yAxis = chart.axisLeft
        val xAxis = chart.xAxis
        xAxis.axisMinimum = balances.first().id.dayOfYear().get().toFloat()
        xAxis.axisMaximum = balances.last().id.dayOfYear().get().toFloat()
        yAxis.axisMaximum = balances.maxBy { it.amount }!!.amount.toFloat() + 5000f
        chart.xAxis.labelCount = balances.count()/2
        chart.invalidate()
    }

    companion object {
        fun newInstance(): BalanceChartFragment {
            return BalanceChartFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.chart_balance, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.observeBalances()
        chart = view.findViewById(R.id.line_chart)
        chart.xAxis.disableGridDashedLine()
        val yAxis = chart.axisLeft
        yAxis.axisMinimum = 0f
        chart.xAxis.disableAxisLineDashedLine()
        chart.xAxis.isEnabled = true
        chart.legend.isEnabled = false
        chart.isDoubleTapToZoomEnabled = false
        chart.xAxis.setDrawLabels(true)
        chart.axisRight.setDrawLabels(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        AppWatcher.objectWatcher.watch(this)
    }

    override fun showError() {
        showEmptyPlaceholder()
    }

    private fun showEmptyPlaceholder() {
        emptyPlaceholder_bar.isVisible = true
        line_chart.isVisible = false
    }

    private fun hidePlaceholder() {
        emptyPlaceholder_bar.isVisible = false
        line_chart.isVisible = true
    }

    @InjectPresenter
    lateinit var presenter: BalancePresenter

    lateinit var chart: LineChart

    @ProvidePresenter
    fun providePresenter() = App.component.getBalancePresenter()

}
