package com.example.holmi_production.money_counter_app.ui.fragments.charts

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.extensions.toCurencyFormat
import com.example.holmi_production.money_counter_app.extensions.withRubleSign
import com.example.holmi_production.money_counter_app.main.BaseFragment
import com.example.holmi_production.money_counter_app.model.PieCharState
import com.example.holmi_production.money_counter_app.model.entity.GraphEntity
import com.example.holmi_production.money_counter_app.ui.fragments.PieDialogFragment
import com.example.holmi_production.money_counter_app.ui.presenters.charts.ChartPieViewModel
import com.example.holmi_production.money_counter_app.utils.ColorUtils
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.chart_pie.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class ChartPieFragment : BaseFragment(R.layout.chart_pie){


    val myviewModel: ChartPieViewModel by viewModel()

    override fun inject() {
   //AppComponent.instance.inject(this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("M_PieChartFragment", "pie view created")
        super.onViewCreated(view, savedInstanceState)
        myviewModel.observeData()
        preparePieSettings()
        chart_pie.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onNothingSelected() {}

            override fun onValueSelected(e: Entry?, h: Highlight?) {
                val categoryId = e!!.data as Int
                val canDetailed = pieBack.visibility == View.VISIBLE
                myviewModel.updateGraph(categoryId, canDetailed)
                Log.d("M_PieChartFragment", "VALUE SELECTED index $categoryId")
            }
        })
        pieBack.setOnClickListener {
            myviewModel.updateGraph(null, true)
        }
    }


//    override fun render(state: PieCharState) {
//        when (state) {
//            is PieCharState.DetailsState -> renderDetailsState(state)
//            is PieCharState.ErrorState -> renderError()
//            is PieCharState.NormalState -> renderNormalState(state)
//        }
//    }

    private fun renderNormalState(state: PieCharState.NormalState) {
        hidePlaceholder()
        showPie(state.values)
        showChips(state.values)
        if (state.canDetailed) {
            chart_pie.setTouchEnabled(true)
            chip_group.isClickable = true
            pieBack.visibility = View.GONE
        } else {
            chart_pie.setTouchEnabled(false)
            chip_group.isClickable = false
            pieBack.visibility = View.VISIBLE
        }
    }

    private fun renderError() {
        showEmptyPlaceholder()
    }

    private fun renderDetailsState(state: PieCharState.DetailsState) {
        hidePlaceholder()
        val bundle = Bundle()
        //TODO get data from repository
//        bundle.putParcelableArray("SPENDINGS", state.spendings)
        val fr =
            PieDialogFragment.newInstance(
                bundle
            )
        fr.show(childFragmentManager, "Spending dialog")
    }


    private fun showChips(data: List<GraphEntity>) {
        chip_group.removeAllViews()
        data.forEach {
            chip_group.addView(
                buildChip(
                    id = it.id,
                    description = it.description,
                    color = it.color,
                    sum = it.sum
                )
            )
        }
    }

    private fun showPie(data: List<GraphEntity>) {
        if (data.isEmpty()) {
            showEmptyPlaceholder()
        } else {
            val first = arrayListOf<PieEntry>()
            val second = arrayListOf<String>()
            val colors = arrayListOf<Int>()
            var allMoney = 0.0
            data.forEach {
                val pieEntry = PieEntry(it.sum.toFloat(), it.description, it.id)
                first.add(pieEntry)
                second.add(it.description)
                colors.add(it.color)
                allMoney += it.sum
                Log.d("M_PieChartFragment", "index ${it.id} name ${it.description}")
            }
            val pieSet = PieDataSet(first.toList(), null)
            val pieData = PieData(pieSet)
            pieSet.sliceSpace = 3f
            pieSet.setDrawValues(false)
            pieSet.colors = colors
            pieSet.isHighlightEnabled = false
            chart_pie.data = pieData
            chart_pie.centerText = allMoney.toCurencyFormat().withRubleSign()
            chart_pie.onTouchListener.setLastHighlighted(null)
            chart_pie.highlightValues(null)
            chart_pie.notifyDataSetChanged()
            chart_pie.animateXY(1000, 1000)
        }
    }

    private fun preparePieSettings() {
        hidePlaceholder()
        chart_pie.isRotationEnabled = false
        chart_pie.description.text = ""
        chart_pie.description.textSize = 20f
        chart_pie.holeRadius = 45f
        chart_pie.setDrawEntryLabels(false)
        chart_pie.legend.isEnabled = false
        chart_pie.setCenterTextSize(35f)
        chart_pie.minAngleForSlices = 5f
    }

    private fun buildChip(id: Int?, description: String, color: Int, sum: Double): Chip {
        val chip = Chip(context)
        val text = "$description ${sum.toCurencyFormat().withRubleSign()}"
        chip.text = text
        chip.setTextColor(ColorUtils.getFontColor(color))
        chip.chipBackgroundColor = ColorStateList.valueOf(color)
        chip.textSize = 20f
        chip.isCheckable = true
//        chip.setOnLongClickListener {
//            presenter.getSpending(id!!)
//            return@setOnLongClickListener true
//        }
//        chip.setOnClickListener {
//            presenter.getSpending(id!!)
//        }
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


    companion object {
        fun newInstance(): ChartPieFragment {
            return ChartPieFragment()
        }
    }
}