package com.example.holmi_production.money_counter_app.costs

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.example.holmi_production.money_counter_app.async
import com.example.holmi_production.money_counter_app.model.Expense
import com.example.holmi_production.money_counter_app.model.Spending
import com.example.holmi_production.money_counter_app.mvp.BasePresenter
import com.example.holmi_production.money_counter_app.sortedByDescending
import com.example.holmi_production.money_counter_app.storage.SpendingRepository
import com.example.holmi_production.money_counter_app.storage.SumPerDayRepository
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.rxkotlin.toObservable
import org.joda.time.DateTime
import javax.inject.Inject

@InjectViewState
class CostsPresenter @Inject constructor(
    private val spRep: SpendingRepository,
    private val pdRep: SumPerDayRepository
) : BasePresenter<CostsView>() {

    fun loadCosts() {
        spRep.observeSpending()
            .async()
            .flatMap { trasform(it) }
            .subscribe ({item -> viewState.showSpending(item)},
                {error -> viewState.onError(error)})
            .keep()
    }

    fun delete(spending: Spending) {
        val time = DateTime().withTimeAtStartOfDay()
        when (spending.categoryTypes) {
            Expense.SALARY -> {
                pdRep.getFromDate(time)
                    .async()
                    .subscribe{
                            sums ->
                        val newSums = sums.toMutableList()
                        val daysCount: Int
                        if (sums[0].sum < spending.sum / sums.count()) {
                            daysCount = sums.count() + 1
                            for (i in 1 until daysCount) {
                                newSums[i].sum = sums[i].sum - spending.sum / daysCount
                            }
                        } else {
                            daysCount = sums.count()
                            for (i in 0 until sums.count()) {
                                newSums[i].sum = sums[i].sum - spending.sum / daysCount
                            }
                        }
                        pdRep.insert(newSums).async().subscribe().keep()
                    }
                    .keep()
            }
            else -> {
                when (spending.spendingDate.dayOfYear()) {
                    DateTime.now().dayOfYear() -> {
                        pdRep.getOnDate(time)
                            .async()
                            .doOnError { t -> Log.d("qwerty", t.toString()) }
                            .subscribe{it ->
                                pdRep.insert(it.inc(spending.sum)).async().subscribe().keep()
                                Log.d("qwerty", "succes")}
                            .keep()
                    }
                    else -> {
                        pdRep.getFromDate(time)
                            .async()
                            .subscribe{sums ->
                                val daysCount = sums.count()
                                val newList = sums.toMutableList()
                                newList.forEach { t -> t.sum += spending.sum / daysCount }
                                pdRep.insert(newList).async().subscribe().keep()}
                            .keep()
                    }
                }
            }
        }
        spRep.delete(spending).async().subscribe { viewState.updateList() }.keep()
    }

    private fun trasform(costs: List<Spending>): Flowable<List<ListItem>> {
        return costs
            .toObservable()
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