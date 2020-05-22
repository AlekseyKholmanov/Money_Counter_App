package com.example.holmi_production.money_counter_app.ui.presenters

import android.util.Log
import com.example.holmi_production.money_counter_app.extensions.async
import com.example.holmi_production.money_counter_app.interactor.SpendingInteractor
import com.example.holmi_production.money_counter_app.model.CostTimeDivider
import com.example.holmi_production.money_counter_app.model.DailyExpenses
import com.example.holmi_production.money_counter_app.model.ListItem
import com.example.holmi_production.money_counter_app.model.SpDirection
import com.example.holmi_production.money_counter_app.model.entity.SpendingDetails
import com.example.holmi_production.money_counter_app.model.entity.SpendingEntity


class CostsPresenter(
    private val spendingInteractor: SpendingInteractor

) {

//    fun observeSpengings() {
//        spendingInteractor.observeSpendingWithType()
//            .map { list ->
//                Triple(
//                    transform(list),
//                    list.filter { it.spending.isSpending == SpDirection.SPENDING }.sumByDouble { it.spending.sum },
//                    list.filter { it.spending.isSpending == SpDirection.INCOME }.sumByDouble { it.spending.sum })
//            }
//            .async()
//            .subscribe({ item ->
//                viewState.showSpending(item.first)
//                    viewState.showSumByDirection(item.second, item.third)
//                Log.d("M_CostsPresenter", "observe spending item " + item.first.count().toString())
//            }, {})
//            .keep()
//    }
//
//    fun delete(spending: SpendingEntity) {
//        spendingInteractor.delete(spending).subscribe({
//        }, {}).keep()
//    }
//
//    private fun transform(list: List<SpendingDetails>): ArrayList<ListItem> {
//        val array = arrayListOf<ListItem>()
//        list
//            .sortedByDescending { it.spending.createdDate }
//            .groupBy {
//                it.spending.createdDate.withTimeAtStartOfDay()
//            }
//            .forEach { (time, test) ->
//                array.add(CostTimeDivider(time, DailyExpenses.calculate(test)) as ListItem)
//                test.forEach {
//                    array.add(it as ListItem)
//                }
//            }
//        return array
//    }
}