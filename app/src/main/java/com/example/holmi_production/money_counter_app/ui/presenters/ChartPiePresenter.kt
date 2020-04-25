package com.example.holmi_production.money_counter_app.ui.presenters

import android.graphics.Color
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.example.holmi_production.money_counter_app.extensions.async
import com.example.holmi_production.money_counter_app.interactor.SpendingInteractor
import com.example.holmi_production.money_counter_app.model.PieCharState
import com.example.holmi_production.money_counter_app.model.SpDirection
import com.example.holmi_production.money_counter_app.model.entity.GraphEntity
import com.example.holmi_production.money_counter_app.model.entity.Nameble
import com.example.holmi_production.money_counter_app.model.entity.Spending
import com.example.holmi_production.money_counter_app.model.entity.SpendingListItem
import com.example.holmi_production.money_counter_app.mvp.BasePresenter
import javax.inject.Inject

@InjectViewState
class ChartPiePresenter @Inject constructor(
    private val spendingInteractor: SpendingInteractor
) : BasePresenter<ChartPieView>() {

    var spendingList = mutableListOf<SpendingListItem>()
    fun observeData() {
        spendingInteractor.observeSpendingWithType()
            .map {
                spendingList = it
                filterList(it, null)
            }
            .map {
                return@map getChartEntities(it)
            }
            .async()
            .subscribe({
                viewState.render(PieCharState.NormalState(it, true))
            }, {
                viewState.render(PieCharState.ErrorState)
                Log.d("qwerty", it.message)
            })
            .keep()
    }

    fun getSpending(categoryId: Int) {
        val filtered = mutableListOf<SpendingListItem>()
        spendingList.forEach { item ->
            if (item.category.id == categoryId)
                filtered.add(item)
        }
        filtered.sortByDescending { it.spending.createdDate }
        Log.d("M_PieChartPresenter", "size: ${filtered.size}")
        viewState.render(PieCharState.DetailsState(filtered.toTypedArray()))
    }

    private fun filterList(
        list: List<SpendingListItem>,
        categoryId: Int?
    ): List<Pair<Nameble?, List<Spending>>> {
        val value = list
            .asSequence()
            .filter { it.spending.isSpending == SpDirection.SPENDING }
            .filter {
                if (categoryId != null)
                    it.category.id == categoryId
                else true
            }
            .groupBy {
                if (categoryId == null)
                    it.category
                else
                    it.subCategory
            }
            .map { Pair(it.key as Nameble?, it.value.map { it.spending }) }
            .sortedByDescending { it.second.sumByDouble { it.sum } }
            .toList()
        return value
    }

    fun updateGraph(categoryId: Int?, canDetailed: Boolean) {
        val sorted = filterList(spendingList, categoryId)
        val output = getChartEntities(sorted)
        viewState.render(PieCharState.NormalState(output, canDetailed))
    }

    private fun getChartEntities(values: List<Pair<Nameble?, List<Spending>>>): List<GraphEntity> {
        val entities = mutableListOf<GraphEntity>()
        values.forEach { (entity, spendings) ->
            entities.add(
                GraphEntity(
                    id = entity?.id, sum = spendings.sumByDouble { it.sum },
                    color = entity?.color ?: Color.DKGRAY,
                    description = entity?.description ?: "othet"
                )
            )
        }
        return entities
    }

}