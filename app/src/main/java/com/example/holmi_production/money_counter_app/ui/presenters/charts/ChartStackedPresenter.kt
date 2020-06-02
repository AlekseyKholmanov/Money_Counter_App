package com.example.holmi_production.money_counter_app.ui.presenters.charts

import com.example.holmi_production.money_counter_app.interactor.SpendingInteractor
import com.example.holmi_production.money_counter_app.model.enums.SpDirection
import com.example.holmi_production.money_counter_app.model.entity.SpendingEntity
import org.joda.time.DateTime


class ChartStackedPresenter (private val spendingInteractor: SpendingInteractor) {

    fun observeDatas() {
//        spendingInteractor.observePeriods()
//            .map { prepareDatas(it) }
//            .async()
//            .subscribe({ list ->
//                viewState.showGraph(list)
//            }, {
//                Log.d("qwerty", it.message)
//                viewState.showError()
//            })
//            .keep()

    }

    private fun prepareDatas(spendings: List<SpendingEntity>): Map<DateTime, List<SpendingEntity>> {
        return spendings
            .filter { it.isSpending == SpDirection.SPENDING }
            .sortedByDescending { it.sum }
            .groupBy { it.createdDate.withTimeAtStartOfDay() }
    }
}