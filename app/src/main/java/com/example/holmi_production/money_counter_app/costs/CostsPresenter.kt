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
import javax.inject.Inject

@InjectViewState
class CostsPresenter @Inject constructor(
    private val spRep: SpendingRepository,
    private val pdRep: SumPerDayRepository
) : BasePresenter<CostsView>() {

    fun loadCosts() {
        spRep.getAll()
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
        spRep.delete(spending)
            .async()
            .subscribe {
                viewState.updateList()
            }
            .keep()
//        if(spending.categoryTypes == Expense.SALARY){
//            pdRep.getFromDate(time)
//                .async()
//                .firstOrError()
//                .subscribe({
//                        it->
//                    perDayRep.insert(SumPerDay(time,it.sum+spending.price))
//                        .async()
//                        .subscribe()
//                        .keep()
//                },{t->Log.d("qwerty",t.toString())})
//                .keep()
//        }
        if(spending.categoryTypes == Expense.SALARY){
            Log.d("qwerty", "ты пидор")
        }
        else{
            Log.d("qwerty","ты гей")
        }
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