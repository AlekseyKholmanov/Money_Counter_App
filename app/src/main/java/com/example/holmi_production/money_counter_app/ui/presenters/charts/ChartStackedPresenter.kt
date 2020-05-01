package com.example.holmi_production.money_counter_app.ui.presenters.charts

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.example.holmi_production.money_counter_app.extensions.async
import com.example.holmi_production.money_counter_app.interactor.SpendingInteractor
import com.example.holmi_production.money_counter_app.model.SpDirection
import com.example.holmi_production.money_counter_app.model.entity.SpendingEntity
import com.example.holmi_production.money_counter_app.mvp.BasePresenter
import com.example.holmi_production.money_counter_app.ui.presenters.StackedView
import org.joda.time.DateTime
import javax.inject.Inject

@InjectViewState
class ChartStackedPresenter @Inject constructor(private val spendingInteractor: SpendingInteractor) :
    BasePresenter<StackedView>() {

    fun observeDatas() {
        spendingInteractor.observePeriods()
            .map { prepareDatas(it) }
            .async()
            .subscribe({ list ->
                viewState.showGraph(list)
            }, {
                Log.d("qwerty", it.message)
                viewState.showError()
            })
            .keep()

    }

    private fun prepareDatas(spendings: List<SpendingEntity>): Map<DateTime, List<SpendingEntity>> {
        return spendings
            .filter { it.isSpending == SpDirection.SPENDING }
            .sortedByDescending { it.sum }
            .groupBy { it.createdDate.withTimeAtStartOfDay() }
    }
}