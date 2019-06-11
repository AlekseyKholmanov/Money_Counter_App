package com.example.holmi_production.money_counter_app.costs

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.example.holmi_production.money_counter_app.async
import com.example.holmi_production.money_counter_app.model.Expense
import com.example.holmi_production.money_counter_app.model.Spending
import com.example.holmi_production.money_counter_app.model.SumPerDay
import com.example.holmi_production.money_counter_app.mvp.BasePresenter
import com.example.holmi_production.money_counter_app.sortedByDescending
import com.example.holmi_production.money_counter_app.storage.SpendingRepository
import com.example.holmi_production.money_counter_app.storage.SumPerDayRepository
import io.reactivex.Flowable
import io.reactivex.rxkotlin.toObservable
import org.joda.time.DateTime
import java.util.*
import javax.inject.Inject

@InjectViewState
class CostsPresenter @Inject constructor(
    private val spRep: SpendingRepository,
    private val pdRep: SumPerDayRepository
) : BasePresenter<CostsView>() {

    fun loadCosts() {
        spRep.getAll().flatMap { trasform(it) }.async()
            .subscribe(
                { item -> viewState.showSpending(item.toMutableList()) },
                { error -> viewState.onError(error) }
            )
            .keep()
    }

    fun delete(spending: Spending) {
        val time = DateTime().withTimeAtStartOfDay()
        when (spending.categoryTypes) {
            Expense.SALARY -> {
                pdRep.getFromDate(time)
                    .async()
                    .doOnSuccess { sums ->
                        val newSums = sums.toMutableList()
                        val daysCount: Int
                        if (sums[0].sum < spending.sum / sums.count()) {
                            daysCount = sums.count()
                            for (i in 1 until daysCount) {
                                newSums[i] = sums[i].copy(sum = sums[i].sum - spending.sum / daysCount)
                            }
                        } else {
                            daysCount = sums.count()
                            for (i in 0 until sums.count()) {
                                newSums[i] = sums[i].copy(sum = sums[i].sum - spending.sum / daysCount)
                            }
                        }
                        pdRep.insert(newSums.toList()).async().subscribe().keep()
                    }
                    .subscribe({}, { t -> Log.d("qwerty", t.toString()) })
                    .keep()
            }
            else -> {
                when (spending.spendingDate.dayOfYear()) {
                    DateTime.now().dayOfYear() -> {
                        pdRep.getByDate(time)
                            .async()
                            .firstOrError()
                            .doOnSuccess { it ->
                                val newSum = it.copy(sum = it.sum + spending.sum)
                                pdRep.insert(newSum)
                                Log.d("qwerty", "succes")
                            }
                            .doOnError { t -> Log.d("qwerty", t.toString()) }
                            .subscribe().keep()
                    }
                    else -> {
                        pdRep.getFromDate(time)
                            .async()
                            .doOnSuccess { sums ->
                                val daysCount = sums.count()
                                val newList = sums.toMutableList()
                                newList.forEach { t -> t.sum += spending.sum / daysCount }
                                pdRep.insert(newList).async().subscribe().keep()
                            }
                            .subscribe().keep()
                    }
                }
            }
        }
        spRep.delete(spending).async().subscribe { viewState.updateList() }.keep()
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