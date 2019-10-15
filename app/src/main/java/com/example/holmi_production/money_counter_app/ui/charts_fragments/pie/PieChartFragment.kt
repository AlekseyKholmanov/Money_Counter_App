package com.example.holmi_production.money_counter_app.ui.charts_fragments.pie

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
import com.example.holmi_production.money_counter_app.model.CategoryType
import com.example.holmi_production.money_counter_app.model.entity.Spending
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.android.synthetic.main.chart_pie.*
import leakcanary.AppWatcher

class PieChartFragment : AndroidXMvpAppCompatFragment(),
    PieChartView {

    companion object {
        fun newInstance(): PieChartFragment {
            return PieChartFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.chart_pie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("M_PieChartFragment", "pie view created")
        super.onViewCreated(view, savedInstanceState)
        presenter.getPieData()
        presenter.observeData()
    }

    override fun onDestroy() {
        super.onDestroy()
        AppWatcher.objectWatcher.watch(this)
    }

    override fun showError() {
        showEmptyPlaceholder()
    }

    private fun showEmptyPlaceholder() {
        emptyPlaceholder_pie.isVisible = true
        chart_pie.isVisible = false
    }

    override fun showPie(data: Map<CategoryType, List<Spending>>) {
        if (data.isEmpty()) {
            showEmptyPlaceholder()
        } else {
            hidePlaceholder()
            chart_pie.description.text = ""
            chart_pie.description.textSize = 20f
            chart_pie.holeRadius = 40f
            chart_pie.isRotationEnabled = false
            val first = arrayListOf<PieEntry>()
            val second = arrayListOf<String>()
            val colors = arrayListOf<Int>()

            data.forEach { (category, spendings) ->
                val sum = spendings.sumByDouble { it.sum }
                first.add(PieEntry(sum.toFloat(), category.description))
                second.add(category.description)
                colors.add(category.color)
            }
            val pieSet = PieDataSet(first.toList(), null)
            val pieData = PieData(pieSet)
            pieSet.valueTextSize = 20f
            pieSet.sliceSpace = 3f
            pieSet.colors = colors
            pieSet.setDrawValues(false)
            chart_pie.data = pieData
            chart_pie.description.textSize = 25f
            chart_pie.setDrawEntryLabels(false)
            chart_pie.legend.textSize = 35f
            chart_pie.animateXY(1000, 1000)
            chart_pie.notifyDataSetChanged()
        }
    }

    @ProvidePresenter
    fun providePresenter() = App.component.getChartPresenter()

    @InjectPresenter
    lateinit var presenter: PieChartPresenter

    private fun hidePlaceholder() {
        emptyPlaceholder_pie.isVisible = false
        chart_pie.isVisible = true
    }

}