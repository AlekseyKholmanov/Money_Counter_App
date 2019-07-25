package com.example.holmi_production.money_counter_app.chart

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.example.holmi_production.money_counter_app.extensions.async
import com.example.holmi_production.money_counter_app.model.CategoryType
import com.example.holmi_production.money_counter_app.mvp.BasePresenter
import com.example.holmi_production.money_counter_app.storage.SpendingRepository
import com.example.holmi_production.money_counter_app.storage.SumPerDayRepository
import javax.inject.Inject

@InjectViewState
class PieChartPresenter @Inject constructor(
    private val spendingRepository: SpendingRepository,
    private val sumPerDayRepository: SumPerDayRepository
) : BasePresenter<PieChartView>() {
    fun getPieData() {
        spendingRepository.getSpent()
            .async()

            .map { it ->
                it.groupBy {CategoryType.values()[it.categoryType] }
            }
            .subscribe ({ it ->
                viewState.showPie(it)
            },{

                Log.d("qwerty",it.message)
            })
            .keep()
    }
}