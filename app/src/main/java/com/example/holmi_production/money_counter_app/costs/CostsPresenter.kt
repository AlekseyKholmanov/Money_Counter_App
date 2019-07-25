package com.example.holmi_production.money_counter_app.costs

import android.os.Build
import android.provider.Settings.System.DATE_FORMAT
import android.util.Log
import androidx.annotation.RequiresApi
import com.arellomobile.mvp.InjectViewState
import com.example.holmi_production.money_counter_app.extensions.async
import com.example.holmi_production.money_counter_app.extensions.complete
import com.example.holmi_production.money_counter_app.mvp.BasePresenter
import com.example.holmi_production.money_counter_app.extensions.sortedByDescending
import com.example.holmi_production.money_counter_app.extensions.toRUformat
import com.example.holmi_production.money_counter_app.model.*
import com.example.holmi_production.money_counter_app.storage.SettingRepository
import com.example.holmi_production.money_counter_app.storage.SpendingRepository
import com.example.holmi_production.money_counter_app.storage.SumPerDayRepository
import io.reactivex.Flowable
import io.reactivex.rxkotlin.toObservable
import org.joda.time.DateTime
import java.util.function.BiConsumer
import javax.inject.Inject

@InjectViewState
class CostsPresenter @Inject constructor(
    private val spendingRepository: SpendingRepository,
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
        when (spending.isSpending) {
            false -> {
                sumPerDayRepository.getBoth()
                    .async()
                    .subscribe({ sums ->
                        val today = sums.first.sum
                        val average = sums.second.sum
                        val deltaAverage = spending.sum / settingRepository.getTillEnd()
                        sumPerDayRepository.insertToday(today - deltaAverage).complete().keep()
                        sumPerDayRepository.insertAverage(average - deltaAverage).complete().keep()
                    }, {
                        Log.d("qwerty", it.message)
                    })
                    .keep()
            }
            else -> {
                when (spending.spendingDate.dayOfYear()) {
                    DateTime.now().dayOfYear() -> {
                        sumPerDayRepository.getToday()
                            .async()
                            .doOnError { t -> Log.d("qwerty", t.toString()) }
                            .subscribe({ it ->
                                sumPerDayRepository.insertToday(it.inc(spending.sum).sum).complete().keep()
                            },
                                {
                                    Log.d("qwerty", it.message)
                                })
                            .keep()
                    }
                    else -> {
                        sumPerDayRepository.getBoth()
                            .async()
                            .subscribe({ sums ->
                                val today = sums.first.sum
                                val average = sums.second.sum
                                val deltaAverage = spending.sum / settingRepository.getTillEnd()
                                sumPerDayRepository.insertToday(today + deltaAverage).complete().keep()
                                sumPerDayRepository.insertAverage(average + deltaAverage).complete().keep()
                            }, {
                                Log.d("qwerty", it.message)
                            })
                            .keep()
                    }
                }
            }
        }
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