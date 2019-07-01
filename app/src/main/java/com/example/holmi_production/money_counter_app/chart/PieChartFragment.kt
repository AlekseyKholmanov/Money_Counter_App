package com.example.holmi_production.money_counter_app.chart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.CategoryType
import com.example.holmi_production.money_counter_app.model.Spending
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.android.synthetic.main.chart_pie_chart.*

class PieChartFragment: AndroidXMvpAppCompatFragment(),PieChartView{


    @InjectPresenter
    lateinit var presenter: PieChartPresenter

    @ProvidePresenter
    fun providePresenter() = App.component.getChartPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.chart_pie_chart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getPieData()
    }

    override fun showPie(data: Map<CategoryType, List<Spending>>) {
        chart_pie.description.text = "Расходы"
        chart_pie.description.textSize = 20f
        chart_pie.holeRadius = 40f
        val first = arrayListOf<PieEntry>()
        val second = arrayListOf<String>()
        val colors = arrayListOf<Int>()

        data.forEach { (category, spendings) ->
            val sum = spendings.sumByDouble { it.sum }
            first.add(PieEntry(sum.toFloat(), category.description))
            second.add(category.description)
            colors.add(category.color)
        }
        val pieSet = PieDataSet(first.toList(),null)
        val pieData = PieData(pieSet)
        pieSet.valueTextSize = 20f
        pieSet.sliceSpace = 2f
        pieSet.colors = colors
        chart_pie.data = pieData
        chart_pie.description.textSize = 25f
        chart_pie.animateXY(1000,1000)

    }
}