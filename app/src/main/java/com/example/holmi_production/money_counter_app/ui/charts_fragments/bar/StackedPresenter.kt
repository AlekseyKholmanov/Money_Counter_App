package com.example.holmi_production.money_counter_app.ui.charts_fragments.bar

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.example.holmi_production.money_counter_app.interactor.SpendingInteractor
import com.example.holmi_production.money_counter_app.model.Spending
import com.example.holmi_production.money_counter_app.mvp.BasePresenter
import org.joda.time.DateTime
import javax.inject.Inject

@InjectViewState
class StackedPresenter @Inject constructor(private val spendingInteractor: SpendingInteractor) :
    BasePresenter<StackedView>() {

    fun getDatas() {
        spendingInteractor.getAllInPeriod()
            .map { prepareDatas(it) }
            .subscribe({ list ->
                viewState.showFraph(list)
            }, {
                Log.d("qwerty", it.message)
                viewState.showError()
            })
            .keep()
    }

    fun observeDatas() {
        spendingInteractor.observePeriods()
            .map { prepareDatas(it) }
            .subscribe({ list ->
                viewState.showFraph(list)
            }, {
                Log.d("qwerty", it.message)
                viewState.showError()
            })
            .keep()

    }

    private fun prepareDatas(spendings: List<Spending>): Map<DateTime, List<Spending>> {
        return spendings
            .filter { it.isSpending }
            .sortedByDescending { it.sum }
            .groupBy { it.createdDate.withTimeAtStartOfDay() }
    }
}