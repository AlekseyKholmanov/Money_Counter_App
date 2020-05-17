package com.example.holmi_production.money_counter_app.ui.presenters.charts

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.ViewModel

import com.example.holmi_production.money_counter_app.extensions.async
import com.example.holmi_production.money_counter_app.interactor.SpendingInteractor
import com.example.holmi_production.money_counter_app.model.PieCharState
import com.example.holmi_production.money_counter_app.model.SpDirection
import com.example.holmi_production.money_counter_app.model.entity.*



class ChartPieViewModel (
    private val spendingInteractor: SpendingInteractor
) : ViewModel() {

    var spendingList = mutableListOf<SpendingDetails>()
    fun observeData() {
//        spendingInteractor.observeSpendingWithType()
//            .map {
//                spendingList = it.toMutableList()
//                filterList(it, null)
//            }
//            .map {
//                return@map getChartEntities(it)
//            }
//            .async()
//            .subscribe({
//                viewState.render(PieCharState.NormalState(it, true))
//            }, {
//                viewState.render(PieCharState.ErrorState)
//                Log.d("qwerty", it.message)
//            })
//            .keep()
    }

    fun getSpending(categoryId: Int) {
//        val filtered = mutableListOf<SpendingDetails>()
//        spendingList.forEach { item ->
//            if (item.category.id == categoryId)
//                filtered.add(item)
//        }
//        filtered.sortByDescending { it.spending.createdDate }
//        Log.d("M_PieChartPresenter", "size: ${filtered.size}")
//        viewState.render(PieCharState.DetailsState(filtered.toTypedArray()))
    }

    private fun filterList(
        list: List<SpendingDetails>,
        categoryId: Int?
    ): List<Pair<Nameble?, List<SpendingEntity>>> {
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
                    it.subcategory
            }
            .map { Pair(it.key as Nameble?, it.value.map { it.spending }) }
            .sortedByDescending { it.second.sumByDouble { it.sum } }
            .toList()
        return value
    }

    fun updateGraph(categoryId: Int?, canDetailed: Boolean) {
        val sorted = filterList(spendingList, categoryId)
        val output = getChartEntities(sorted)
//        viewState.render(PieCharState.NormalState(output, canDetailed))
    }

    private fun getChartEntities(values: List<Pair<Nameble?, List<SpendingEntity>>>): List<GraphEntity> {
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