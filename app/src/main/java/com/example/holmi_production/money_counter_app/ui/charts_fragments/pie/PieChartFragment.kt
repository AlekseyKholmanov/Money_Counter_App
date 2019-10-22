package com.example.holmi_production.money_counter_app.ui.charts_fragments.pie

import android.content.res.ColorStateList
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
import com.example.holmi_production.money_counter_app.extensions.toCurencyFormat
import com.example.holmi_production.money_counter_app.extensions.withRubleSign
import com.example.holmi_production.money_counter_app.model.CategoryType
import com.example.holmi_production.money_counter_app.model.entity.Category
import com.example.holmi_production.money_counter_app.model.entity.SpendingWithCategory
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.chart_pie.*
import leakcanary.AppWatcher

class PieChartFragment : AndroidXMvpAppCompatFragment(),
    PieChartView {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.chart_pie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("M_PieChartFragment", "pie view created")
        super.onViewCreated(view, savedInstanceState)
        presenter.observeData()
    }

    override fun onDestroy() {
        super.onDestroy()
        AppWatcher.objectWatcher.watch(this)
    }

    override fun showError() {
        showEmptyPlaceholder()
    }

    override fun showChips(data: List<Pair<Category, List<SpendingWithCategory>>>) {
        chip_group.removeAllViews()
        data.forEach { (category, spendings) ->
            chip_group.addView(buildChip(category, spendings.sumByDouble { it.spending.sum }))
        }
    }

    override fun showPie(data: List<Pair<Category, List<SpendingWithCategory>>>) {
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
            var allMoney = 0.0
            data.forEach { (category, spendings) ->
                val sum = spendings.sumByDouble { it.spending.sum }
                first.add(PieEntry(sum.toFloat(), category.description))
                second.add(category.description)
                colors.add(category.color!!)
                allMoney+= sum
            }
            val pieSet = PieDataSet(first.toList(), null)
            val pieData = PieData(pieSet)
            pieSet.sliceSpace = 3f
            pieSet.colors = colors
            pieSet.setDrawValues(false)
            chart_pie.setDrawEntryLabels(false)
            chart_pie.data = pieData
            chart_pie.setCenterTextSize(35f)
            chart_pie.centerText = allMoney.toCurencyFormat().withRubleSign()
            chart_pie.legend.isEnabled = false
            chart_pie.minAngleForSlices = 5f
            chart_pie.animateXY(1000, 1000)
            chart_pie.notifyDataSetChanged()
        }
    }

    private fun buildChip(type: Category, sum: Double): Chip {
        val chip = Chip(context)
        val text = "${type.description} ${sum.toCurencyFormat().withRubleSign()}"
        chip.text = text
        chip.chipBackgroundColor= ColorStateList.valueOf(type.color!!)
        chip.textSize = 20f
        return chip
    }

    private fun hidePlaceholder() {
        emptyPlaceholder_pie.isVisible = false
        chart_pie.isVisible = true
    }

    private fun showEmptyPlaceholder() {
        emptyPlaceholder_pie.isVisible = true
        chart_pie.isVisible = false
    }

    @ProvidePresenter
    fun providePresenter() = App.component.getChartPresenter()

    @InjectPresenter
    lateinit var presenter: PieChartPresenter

    companion object {
        fun newInstance(): PieChartFragment {
            return PieChartFragment()
        }
    }
}