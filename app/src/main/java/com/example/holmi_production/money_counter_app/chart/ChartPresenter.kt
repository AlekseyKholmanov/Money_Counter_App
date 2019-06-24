package com.example.holmi_production.money_counter_app.chart

import com.arellomobile.mvp.InjectViewState
import com.example.holmi_production.money_counter_app.async
import com.example.holmi_production.money_counter_app.model.CategoryType
import com.example.holmi_production.money_counter_app.mvp.BasePresenter
import com.example.holmi_production.money_counter_app.storage.SpendingRepository
import com.example.holmi_production.money_counter_app.storage.SumPerDayRepository
import javax.inject.Inject

@InjectViewState
class ChartPresenter @Inject constructor(
    private val spendingRepository: SpendingRepository,
    private val sumPerDayRepository: SumPerDayRepository
) : BasePresenter<ChartView>() {
    fun getPieData() {
        spendingRepository.getSpent()
            .async()

            .map { it ->
                it.groupBy { it.categoryTypes }
            }
            .subscribe { it ->
                viewState.showPie(it)
            }
            .keep()
    }
}