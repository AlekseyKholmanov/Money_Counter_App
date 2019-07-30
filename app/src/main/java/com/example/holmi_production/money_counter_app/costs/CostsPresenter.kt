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
import com.example.holmi_production.money_counter_app.storage.SettingRepository
import com.example.holmi_production.money_counter_app.storage.SpendingRepository
import com.example.holmi_production.money_counter_app.storage.SumPerDayRepository
import javax.inject.Inject

@InjectViewState
class CostsPresenter @Inject constructor(
    private val spendingRepository: SpendingRepository,
    private val spendingInteractor: SpendingInteractor,
    private val sumPerDayRepository: SumPerDayRepository,
    private val settingRepository: SettingRepository
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

    fun delete(spending: Spending) {
        spendingInteractor.delete(spending).subscribe().keep()
        this.spendingRepository.delete(spending).async().subscribe { viewState.updateList() }.keep()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun newTransform(costs: List<Spending>): ArrayList<ListItem> {
        val list = arrayListOf<ListItem>()
        costs
            .groupBy { it.spendingDate.withTimeAtStartOfDay() }
            .toSortedMap(Comparator { o1, o2 -> o2.compareTo(o1) })
            .forEach { (t, u) ->
                list.add(CostTimeDivider(t.toRUformat(), DailyExpenses.calculate(u)) as ListItem)
                val mutable = u.toMutableList().also { it -> it.sortByDescending { it.spendingDate } }
                mutable.forEach { list.add(it as ListItem) }
            }
        return list
    }
}