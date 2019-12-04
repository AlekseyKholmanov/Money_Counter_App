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
import com.example.holmi_production.money_counter_app.model.entity.Category
import com.example.holmi_production.money_counter_app.model.entity.Spending
import com.example.holmi_production.money_counter_app.model.entity.SpendingListItem
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.chart_pie.*
import leakcanary.AppWatcher

class PieChartFragment : AndroidXMvpAppCompatFragment(),
    PieChartView {
    override fun showDetails(items: Array<SpendingListItem>) {
        val bundle = Bundle()
        bundle.putParcelableArray("SPENDINGS", items)
        val fr = PieDialogFragment.newInstance(bundle)
        fr.show(childFragmentManager, "Spending dialog")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.chart_pie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("M_PieChartFragment", "pie view created")
        super.onViewCreated(view, savedInstanceState)
        chart_pie.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onNothingSelected() {
                Log.d("M_PieChartFragment", "NOTHING CALLBACK")
            }

            override fun onValueSelected(e: Entry?, h: Highlight?) {
                val categoryId = e!!.data as Int
                presenter.getSpending(categoryId)
                Log.d("M_PieChartFragment", "VALUE SELECTED index $categoryId")
            }
        })
        presenter.observeData()
    }

    override fun onDestroy() {
        super.onDestroy()
        AppWatcher.objectWatcher.watch(this)
        Log.d("M_PieChartFragment", "destroy")
    }

    override fun showError() {
        showEmptyPlaceholder()
    }

    override fun showChips(data: List<Pair<Category?, List<Spending>>>) {
        chip_group.removeAllViews()
        data.forEach { (category, spendings) ->
            chip_group.addView(buildChip(category!!, spendings.sumByDouble { it.sum }))
        }
    }

    override fun showPie(data: List<Pair<Category?, List<Spending>>>) {
        if (data.isEmpty()) {
            showEmptyPlaceholder()
        } else {
            hidePlaceholder()
            chart_pie.description.text = ""
            chart_pie.description.textSize = 20f
            chart_pie.holeRadius = 45f
            chart_pie.isRotationEnabled = false
            val first = arrayListOf<PieEntry>()
            val second = arrayListOf<String>()
            val colors = arrayListOf<Int>()
            var allMoney = 0.0
            data.forEach { (category, spendings) ->
                val sum = spendings.sumByDouble { it.sum }
                val pieEntry = PieEntry(sum.toFloat(), category!!.description, category.id)
                first.add(pieEntry)
                second.add(category.description)
                colors.add(category.color!!)
                allMoney += sum
                Log.d("M_PieChartFragment", "index ${category.id} name ${category.description}")
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
        chip.chipBackgroundColor = ColorStateList.valueOf(type.color!!)
        chip.textSize = 20f
        chip.isCheckable = true
        chip.setOnClickListener {
            presenter.getSpending(type.id!!)
        }
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