package com.example.holmi_production.money_counter_app.ui.charts_fragments.pie

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.example.holmi_production.money_counter_app.extensions.async
import com.example.holmi_production.money_counter_app.interactor.CategoryInteractor
import com.example.holmi_production.money_counter_app.interactor.SpendingInteractor
import com.example.holmi_production.money_counter_app.model.entity.Category
import com.example.holmi_production.money_counter_app.model.entity.SpendingWithCategory
import com.example.holmi_production.money_counter_app.mvp.BasePresenter
import javax.inject.Inject

@InjectViewState
class PieChartPresenter @Inject constructor(
    private val spendingInteractor: SpendingInteractor
) : BasePresenter<PieChartView>() {

    fun observeData() {
        spendingInteractor.observePeiodWithType()
            .async()
            .map {
                filterList((it))
            }
            .subscribe({
                viewState.showPie(it)
                viewState.showChips(it)
            }, {
                viewState.showError()
                Log.d("qwerty", it.message)
            })
            .keep()
    }

    private fun filterList(list: List<SpendingWithCategory>): List<Pair<Category, List<SpendingWithCategory>>> {
        return list
            .filter { it.spending.isSpending }
            .groupBy { it.category.first()}
            .map {it.toPair()}


    }
}