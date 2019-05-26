package com.example.holmi_production.money_counter_app.costs

import com.arellomobile.mvp.InjectViewState
import com.example.holmi_production.money_counter_app.async
import com.example.holmi_production.money_counter_app.model.Spending
import com.example.holmi_production.money_counter_app.mvp.BasePresenter
import com.example.holmi_production.money_counter_app.storage.SpendingRepository
import io.reactivex.Single
import io.reactivex.rxkotlin.toObservable
import javax.inject.Inject

@InjectViewState
class CostsPresenter @Inject constructor(
    private val repository: SpendingRepository
) : BasePresenter<CostsView>() {

    fun loadCosts() {
        repository.getAll()
            .flatMap {
                trasform(it)
            }
            .async()
            .subscribe({ item ->
                viewState.showSpending(item)
            },
                { error ->
                    viewState.onError(error)
                })
            .keep()
    }

    private fun trasform(costs: List<Spending>): Single<List<ListItem>> {
        return costs.toObservable()
            .groupBy { it.spendingDate.toLocalDate() }
            .flatMap { group ->
                group
                    .cast(ListItem::class.java)
                    .startWith(CostTimeDivider(group.key!!.toString(DATE_FORMAT)))
            }
            .toList()
    }

    companion object {
        private const val DATE_FORMAT = "dd MMMMM, yyyy"
    }
}