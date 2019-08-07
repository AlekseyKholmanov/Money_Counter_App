package com.example.holmi_production.money_counter_app.costs

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.arellomobile.mvp.InjectViewState
import com.example.holmi_production.money_counter_app.extensions.async
import com.example.holmi_production.money_counter_app.extensions.toRUformat
import com.example.holmi_production.money_counter_app.interactor.SpendingInteractor
import com.example.holmi_production.money_counter_app.model.CostTimeDivider
import com.example.holmi_production.money_counter_app.model.DailyExpenses
import com.example.holmi_production.money_counter_app.model.ListItem
import com.example.holmi_production.money_counter_app.model.Spending
import com.example.holmi_production.money_counter_app.mvp.BasePresenter
import com.example.holmi_production.money_counter_app.storage.SpendingRepository
import javax.inject.Inject

@InjectViewState
class CostsPresenter @Inject constructor(
    private val spendingRepository: SpendingRepository,
    private val spendingInteractor: SpendingInteractor
) : BasePresenter<CostsView>() {

    @RequiresApi(Build.VERSION_CODES.N)
    fun loadCosts() {
        spendingRepository.observeSpending()
            .async()
            .map { newTransform(it) }
            .subscribe({ item -> viewState.showSpending(item) },
                { error ->
                    viewState.onError(error)
                    Log.d("qwerty", error.message)
                })
            .keep()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getSpending() {
        spendingInteractor.getAllInPeriod()
            .map { newTransform(it) }
            .subscribe({ item -> viewState.showSpending(item) },
                { error ->
                    viewState.onError(error)
                    Log.d("qwerty", error.message)
                })
            .keep()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun setObservers() {
        observeSpendings()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun observeSpendings() {
        spendingInteractor.observePeriods()
            .map { newTransform(it) }
            .subscribe({ item ->
                viewState.showSpending(item)
                Log.d("M_CostsPresenter","observe spending item" + item.count().toString())
            },
                { error ->
                    viewState.onError(error)
                    Log.d("M_CostsPresenter", error.message)
                })
            .keep()
    }

    fun delete(spending: Spending) {
        spendingInteractor.delete(spending).subscribe().keep()
        this.spendingRepository.delete(spending).async().subscribe { viewState.updateList() }.keep()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun newTransform(costs: List<Spending>): ArrayList<ListItem> {
        val list = arrayListOf<ListItem>()
        costs
            .groupBy { it.createdDate.withTimeAtStartOfDay() }
            .toSortedMap(Comparator { o1, o2 -> o2.compareTo(o1) })
            .forEach { (t, u) ->
                list.add(CostTimeDivider(t.toRUformat(), DailyExpenses.calculate(u)) as ListItem)
                val mutable = u.toMutableList().also { it -> it.sortByDescending { it.createdDate } }
                mutable.forEach { list.add(it as ListItem) }
            }
        return list
    }
}