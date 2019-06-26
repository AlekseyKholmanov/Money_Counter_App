package com.example.holmi_production.money_counter_app.costs

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.example.holmi_production.money_counter_app.extensions.async
import com.example.holmi_production.money_counter_app.extensions.complete
import com.example.holmi_production.money_counter_app.model.CategoryType
import com.example.holmi_production.money_counter_app.model.Spending
import com.example.holmi_production.money_counter_app.mvp.BasePresenter
import com.example.holmi_production.money_counter_app.sortedByDescending
import com.example.holmi_production.money_counter_app.storage.SettingRepository
import com.example.holmi_production.money_counter_app.storage.SpendingRepository
import com.example.holmi_production.money_counter_app.storage.SumPerDayRepository
import io.reactivex.Flowable
import io.reactivex.rxkotlin.toObservable
import org.joda.time.DateTime
import javax.inject.Inject

@InjectViewState
class CostsPresenter @Inject constructor(
    private val spendingRepository: SpendingRepository,
    private val sumPerDayRepository: SumPerDayRepository,
    private val settingRepository: SettingRepository
) : BasePresenter<CostsView>() {

    fun loadCosts() {
        spendingRepository.observeSpending()
            .async()
            .flatMap { trasform(it) }
            .subscribe({ item -> viewState.showSpending(item) },
                { error -> viewState.onError(error) })
            .keep()
    }

    fun delete(spending: Spending) {
        when (spending.categoryTypes) {
            CategoryType.SALARY -> {
                sumPerDayRepository.getBoth()
                    .async()
                    .subscribe { sums ->
                        val today = sums.first.sum
                        val average = sums.second.sum
                        val deltaAverage = spending.sum / settingRepository.getTillEnd()
                        sumPerDayRepository.insertToday(today - deltaAverage).complete().keep()
                        sumPerDayRepository.insertAverage(average - deltaAverage).complete().keep()
                    }
                    .keep()
            }
            else -> {
                when (spending.spendingDate.dayOfYear()) {
                    DateTime.now().dayOfYear() -> {
                        sumPerDayRepository.getToday()
                            .async()
                            .doOnError { t -> Log.d("qwerty", t.toString()) }
                            .subscribe { it ->
                                sumPerDayRepository.insertToday(it.inc(spending.sum).sum).complete().keep()
                            }
                            .keep()
                    }
                    else -> {
                        sumPerDayRepository.getBoth()
                            .async()
                            .subscribe { sums ->
                                val today = sums.first.sum
                                val average = sums.second.sum
                                val deltaAverage = spending.sum / settingRepository.getTillEnd()
                                sumPerDayRepository.insertToday(today + deltaAverage).complete().keep()
                                sumPerDayRepository.insertAverage(average + deltaAverage).complete().keep()
                            }
                            .keep()
                    }
                }
            }
        }
        this.spendingRepository.delete(spending).async().subscribe { viewState.updateList() }.keep()
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