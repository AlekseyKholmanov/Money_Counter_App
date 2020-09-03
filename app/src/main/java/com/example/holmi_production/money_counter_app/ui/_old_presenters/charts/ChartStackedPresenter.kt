package com.example.holmi_production.money_counter_app.ui._old_presenters.charts

import com.example.holmi_production.money_counter_app.interactor.SpendingInteractor
import com.example.holmi_production.money_counter_app.model.entity.TransactionEntity
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

    private fun prepareDatas(transactions: List<TransactionEntity>): Map<DateTime, List<TransactionEntity>> {
        return transactions
            .filter { it.sum > 0 }
            .sortedByDescending { it.sum }
            .groupBy { it.createdDate.withTimeAtStartOfDay() }
    }
}