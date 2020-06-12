package com.example.holmi_production.money_counter_app.ui.presenters.charts

import android.graphics.Color
import androidx.lifecycle.ViewModel

import com.example.holmi_production.money_counter_app.interactor.SpendingInteractor
import com.example.holmi_production.money_counter_app.model.GraphItem
import com.example.holmi_production.money_counter_app.model.TransactionDetails
import com.example.holmi_production.money_counter_app.model.enums.SpDirection
import com.example.holmi_production.money_counter_app.model.entity.*



class ChartPieViewModel (
    private val spendingInteractor: SpendingInteractor
) : ViewModel() {

    var spendingList = mutableListOf<TransactionDetails>()
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
        list: List<TransactionDetails>,
        categoryId: Int?
    ): List<Pair<Nameble?, List<TransactionEntity>>> {
        val value = list
            .asSequence()
            .filter { it.transaction.sum > 0 }
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
            .map { Pair(it.key as Nameble?, it.value.map { it.transaction }) }
            .sortedByDescending { it.second.sumByDouble { it.sum } }
            .toList()
        return value
    }

    fun updateGraph(categoryId: Int?, canDetailed: Boolean) {
        val sorted = filterList(spendingList, categoryId)
        val output = getChartEntities(sorted)
//        viewState.render(PieCharState.NormalState(output, canDetailed))
    }

    private fun getChartEntities(values: List<Pair<Nameble?, List<TransactionEntity>>>): List<GraphItem> {
        val entities = mutableListOf<GraphItem>()
        values.forEach { (entity, spendings) ->
            entities.add(
                GraphItem(
                    id = entity?.id, sum = spendings.sumByDouble { it.sum },
                    color = entity?.color ?: Color.DKGRAY,
                    description = entity?.description ?: "othet"
                )
            )
        }
        return entities
    }

}