package com.example.holmi_production.money_counter_app.ui.charts_fragments.pie

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.example.holmi_production.money_counter_app.extensions.async
import com.example.holmi_production.money_counter_app.interactor.SpendingInteractor
import com.example.holmi_production.money_counter_app.model.SpDirection
import com.example.holmi_production.money_counter_app.model.entity.Category
import com.example.holmi_production.money_counter_app.model.entity.Spending
import com.example.holmi_production.money_counter_app.model.entity.SpendingListItem
import com.example.holmi_production.money_counter_app.mvp.BasePresenter
import javax.inject.Inject

@InjectViewState
class PieChartPresenter @Inject constructor(
    private val spendingInteractor: SpendingInteractor
) : BasePresenter<PieChartView>() {

    fun observeData() {
        spendingInteractor.observeSpendingWithType()
            .map {
                filterList((it))
            }
            .async()
            .subscribe({
                viewState.showPie(it)
                viewState.showChips(it)
            }, {
                viewState.showError()
                Log.d("qwerty", it.message)
            })
            .keep()
    }

    private fun filterList(list: List<SpendingListItem>): List<Pair<Category?, List<Spending>>> {
        return list
            .filter { it.spending.isSpending  == SpDirection.SPENDING}
            .groupBy { it.category}
            .map { Pair(it.key, it.value.map { it.spending }) }
            .sortedByDescending { it.second.sumByDouble { it.sum } }
    }
}