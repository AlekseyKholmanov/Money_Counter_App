package com.example.holmi_production.money_counter_app.costs

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.example.holmi_production.money_counter_app.async
import com.example.holmi_production.money_counter_app.model.Spending
import com.example.holmi_production.money_counter_app.mvp.BasePresenter
import com.example.holmi_production.money_counter_app.sortedByDescending
import com.example.holmi_production.money_counter_app.storage.SpendingRepository
import io.reactivex.Flowable
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
            .subscribe(
                { item ->
                    viewState.showSpending(item.toMutableList())
                },
                { error ->
                    viewState.onError(error)
                }
            )
            .keep()
    }

    fun delete(spending: Spending) {
        repository.delete(spending)
            .async()
            .subscribe {
                viewState.updateList()
            }
            .keep()
    }

    private fun trasform(costs: List<Spending>): Flowable<List<ListItem>> {
        return costs.toObservable()
            .groupBy { it.spendingDate.toLocalDate() }
            .sortedByDescending { it.key!! }
            .flatMap { group ->
                group
                    .sortedByDescending { it.spendingDate }
                    .cast(ListItem::class.java)
                    .startWith(CostTimeDivider(group.key!!.toString(DATE_FORMAT)))

            }
            .toList()
            .toFlowable()
    }

    companion object {
        private const val DATE_FORMAT = "dd MMMMM, yyyy"
    }
}