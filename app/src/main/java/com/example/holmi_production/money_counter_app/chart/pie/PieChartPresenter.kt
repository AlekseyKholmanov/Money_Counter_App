package com.example.holmi_production.money_counter_app.chart.pie

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.example.holmi_production.money_counter_app.extensions.async
import com.example.holmi_production.money_counter_app.interactor.SpendingInteractor
import com.example.holmi_production.money_counter_app.model.CategoryType
import com.example.holmi_production.money_counter_app.model.Spending
import com.example.holmi_production.money_counter_app.mvp.BasePresenter
import javax.inject.Inject

@InjectViewState
class PieChartPresenter @Inject constructor(
    private val spendingInteractor: SpendingInteractor
) : BasePresenter<PieChartView>() {
    fun getPieData() {
        spendingInteractor.getAllInPeriod()
            .async()
            .map {
                filterList((it))
            }
            .subscribe({
                viewState.showPie(it)
            }, {
                viewState.showError()
                Log.d("qwerty", it.message)
            })
            .keep()
    }

    fun observeData() {
        spendingInteractor.observePeriods()
            .async()
            .map {
                filterList((it))
            }
            .subscribe({
                viewState.showPie(it)
            }, {
                viewState.showError()
                Log.d("qwerty", it.message)
            })
            .keep()
    }

    private fun filterList(list: List<Spending>): Map<CategoryType, List<Spending>> {
        val nList = list.filter { it.isSpending }
        return nList.groupBy { CategoryType.values()[it.categoryType] }
    }
}