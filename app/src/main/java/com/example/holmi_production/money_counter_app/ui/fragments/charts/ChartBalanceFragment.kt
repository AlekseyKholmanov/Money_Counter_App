package com.example.holmi_production.money_counter_app.ui.fragments.charts

import android.graphics.Color
import android.graphics.DashPathEffect
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.custom.ChartMarkerView
import com.example.holmi_production.money_counter_app.extensions.toCurencyFormat
import com.example.holmi_production.money_counter_app.extensions.withRubleSign
import com.example.holmi_production.money_counter_app.main.BaseFragment
import com.example.holmi_production.money_counter_app.model.entity.BalanceEntity
import com.example.holmi_production.money_counter_app.ui.presenters.charts.ChartBalanceViewModel
import com.example.holmi_production.money_counter_app.utils.Point.getLabelIndexes
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.Utils
import kotlinx.android.synthetic.main.chart_balance.*
import kotlinx.android.synthetic.main.chart_bar.emptyPlaceholder_bar

import org.joda.time.DateTime
import org.koin.androidx.viewmodel.ext.android.viewModel


import kotlin.collections.ArrayList

class ChartBalanceFragment : BaseFragment(R.layout.chart_balance) {

    override fun inject() {
   //AppComponent.instance.inject(this)
    }


    val meViewModel: ChartBalanceViewModel by viewModel()

    lateinit var chart: LineChart


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
   //AppComponent.instance.inject(this)
        meViewModel.observeBalances()
        chart = view.findViewById(R.id.line_chart)
        chart.xAxis.disableGridDashedLine()
        chart.xAxis.disableAxisLineDashedLine()
        chart.xAxis.isEnabled = true
        chart.legend.isEnabled = false
        chart.isDoubleTapToZoomEnabled = false
        chart.xAxis.setDrawLabels(true)
        chart.axisRight.setDrawLabels(false)
        //chart.onTouchListener = null
        chart.setScaleEnabled(false)
        chart.isDragEnabled = true
        Log.d("M_BalanceChartFragment", "on view created")
    }


//    override fun showChart(balances: List<BalanceEntity>) {
//        hidePlaceholder()
//        val values = ArrayList<Entry>()
//        for (i in balances.indices) {
//            val value: Float = balances[i].amount.toFloat()
//            values.add(Entry((balances[i].id.dayOfYear().get()).toFloat(), value))
//        }
//        if (chart.data != null && chart.data.dataSetCount > 0) {
//            val dataSet: LineDataSet = chart.data.getDataSetByIndex(0) as LineDataSet
//            dataSet.values = values
//            dataSet.setDrawFilled(true)
//        } else {
//            val dataSet = LineDataSet(values, "LABEL")
//            dataSet.setDrawIcons(false)
//            dataSet.enableDashedLine(10f, 5f, 0f)
//            dataSet.color = Color.BLACK
//            dataSet.setCircleColor(Color.BLACK)
//            dataSet.lineWidth = 1f
//            dataSet.setDrawCircleHole(true)
//            dataSet.circleHoleRadius = 1f
//            dataSet.circleRadius = 2f
//            dataSet.formLineWidth = 1f
//            dataSet.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
//            dataSet.formSize = 25f
//
//            // text size of values
//            dataSet.valueTextSize = 9f
//
//            if (Utils.getSDKInt() >= 18) {
//                // drawables only supported on api level 18 and above
//                val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.fade_red)
//                dataSet.fillDrawable = drawable
//            } else {
//                dataSet.fillColor = Color.BLACK
//            }
//
//            val dataSets = ArrayList<ILineDataSet>()
//            dataSet.setDrawFilled(true)
//            dataSets.add(dataSet) // add the data sets
//
//            // create a data object with the data sets
//            val data = LineData(dataSets)
//
//            // set data
//            chart.data = data
//        }
//
//        val mv = ChartMarkerView(context, R.layout.item_marker_view)
//
//        // Set the marker to the chart
//        mv.chartView = chart
//        chart.marker = mv
//
//        val yAxis = chart.axisLeft
//        val xAxis = chart.xAxis
//        //min an max x Axis with format to date dd.MM
//        xAxis.axisMinimum = balances.first().id.dayOfYear().get().toFloat()
//        xAxis.axisMaximum = balances.last().id.dayOfYear().get().toFloat()
//        xAxis.valueFormatter = XAxisFormatter()
//
//        //min and max y Axis
//        val yBottom = (balances.minBy { it.amount }?.amount!!)
//        yAxis.axisMaximum = balances.maxBy { it.amount }!!.amount.toFloat() + 3000f
//        yAxis.axisMinimum = yBottom.toFloat() - 3000f
//
//        // labels xAxis count
//        val labelIndexs = getLabelIndexes(balances)
//        val xAxisLabelCount =
//            if (balances.size > 100) 10 else balances.size / 10 + (balances.size / 5)
//        chart.xAxis.labelCount = xAxisLabelCount
//
//        chart.data.setValueFormatter(PointFormatter(labelIndexs, balances.size))
//
//        chart.notifyDataSetChanged()
//        chart.invalidate()
//    }
//
//    override fun showError() {
//        showEmptyPlaceholder()
//    }

    private fun showEmptyPlaceholder() {
        emptyPlaceholder_bar.isVisible = true
        line_chart.isVisible = false
    }

    private fun hidePlaceholder() {
        emptyPlaceholder_bar.isVisible = false
        line_chart.isVisible = true
    }



    inner class XAxisFormatter : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            val date = DateTime().withDayOfYear(value.toInt())
            val day = date.dayOfMonth().get()
            val month = date.monthOfYear().get()
            return "$day.$month"
        }
    }

    inner class PointFormatter(private val labelIndexes: List<Int>, private val size: Int) :
        ValueFormatter() {
        var position = 0

        override fun getFormattedValue(value: Float): String {
            if (position == size)
                position = 0
            position++
            return if (labelIndexes.contains(position)) value.toDouble().toCurencyFormat().withRubleSign() else ""
        }
    }

    companion object {
        fun newInstance(): ChartBalanceFragment {
            return ChartBalanceFragment()
        }
    }
}
