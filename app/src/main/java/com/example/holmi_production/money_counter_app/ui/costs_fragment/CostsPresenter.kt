package com.example.holmi_production.money_counter_app.ui.costs_fragment

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.arellomobile.mvp.InjectViewState
import com.example.holmi_production.money_counter_app.extensions.async
import com.example.holmi_production.money_counter_app.extensions.toRUformat
import com.example.holmi_production.money_counter_app.interactor.SpendingInteractor
import com.example.holmi_production.money_counter_app.model.CategoryType
import com.example.holmi_production.money_counter_app.model.CostTimeDivider
import com.example.holmi_production.money_counter_app.model.DailyExpenses
import com.example.holmi_production.money_counter_app.model.ListItem
import com.example.holmi_production.money_counter_app.model.entity.Spending
import com.example.holmi_production.money_counter_app.model.entity.SpendingWithCategory
import com.example.holmi_production.money_counter_app.mvp.BasePresenter
import com.example.holmi_production.money_counter_app.storage.SpendingRepository
import javax.inject.Inject

@InjectViewState
class CostsPresenter @Inject constructor(
    private val spendingInteractor: SpendingInteractor
) : BasePresenter<CostsView>() {

    @RequiresApi(Build.VERSION_CODES.N)
    fun setObservers() {
        observeSp()
    }


    @RequiresApi(Build.VERSION_CODES.N)
    private fun observeSp(){
        spendingInteractor.observePeiodWithType().map { list ->
            Triple(
                newTransform(list),
                list.filter { it.spending.isSpending }.sumByDouble { it.spending.sum },
                list.filter { !it.spending.isSpending }.sumByDouble { it.spending.sum })
        }
            .subscribe({ item ->
                viewState.showSpending(item.first)
                    viewState.showSumByDirection(item.second,item.third)
                Log.d("M_CostsPresenter", "observe spending item" + item.first.count().toString())
            },
                { error ->
                    viewState.onError(error)
                    Log.d("M_CostsPresenter", error.message)
                })
            .keep()
    }



    fun delete(spending: SpendingWithCategory) {
        spendingInteractor.delete(spending.spending).subscribe ({ viewState.updateList() },{}).keep()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun newTransform(costs: List<SpendingWithCategory>): ArrayList<ListItem> {
        val list = arrayListOf<ListItem>()
        costs
            .groupBy { it.spending.createdDate.withTimeAtStartOfDay() }
            .toSortedMap(Comparator { o1, o2 -> o2.compareTo(o1) })
            .forEach { (t, u) ->
                list.add(CostTimeDivider(t.toRUformat(), DailyExpenses.calculate(u)) as ListItem)
                val mutable = u.toMutableList().also { it -> it.sortByDescending { it.spending.createdDate } }
                mutable.forEach { list.add(it as ListItem) }
            }
        return list
    }
}