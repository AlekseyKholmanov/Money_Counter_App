package com.example.holmi_production.money_counter_app.chart

import com.arellomobile.mvp.InjectViewState
import com.example.holmi_production.money_counter_app.interactor.SpendingInteractor
import com.example.holmi_production.money_counter_app.model.CategoryType
import com.example.holmi_production.money_counter_app.mvp.BasePresenter
import org.joda.time.DateTime
import javax.inject.Inject

@InjectViewState
class StackedPresenter @Inject constructor(private val spendingInteractor: SpendingInteractor) :
    BasePresenter<StackedView>() {

    fun getDatas() {
        spendingInteractor.getAll()
            .map { it.filter { it.categoryTypes != CategoryType.SALARY } }
            .map { it.filter { it.spendingDate >= DateTime().minusDays(5) } }
            .map { it.groupBy { it.spendingDate.withTimeAtStartOfDay() } }
            .subscribe { list ->
                viewState.showFraph(list)
            }
            .keep()

    }
}