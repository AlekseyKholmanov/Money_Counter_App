package com.example.holmi_production.money_counter_app.interactor

import android.util.Log
import com.example.holmi_production.money_counter_app.extensions.async
import com.example.holmi_production.money_counter_app.extensions.complete
import com.example.holmi_production.money_counter_app.model.entity.Spending
import com.example.holmi_production.money_counter_app.model.entity.SpendingListItem
import com.example.holmi_production.money_counter_app.storage.PeriodsRepository
import com.example.holmi_production.money_counter_app.storage.SettingRepository
import com.example.holmi_production.money_counter_app.storage.SpendingRepository
import com.example.holmi_production.money_counter_app.storage.SumPerDayRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.Flowables
import org.joda.time.DateTime
import javax.inject.Inject

class SpendingInteractor @Inject constructor(
    private val spendingRepository: SpendingRepository,
    private val sumPerDayRepository: SumPerDayRepository,
    private val settingRepository: SettingRepository,
    private val periodsRepository: PeriodsRepository,
    private val categoryInteractor: CategoryInteractor) {

    fun insert(spending: Spending): Completable {
        return spendingRepository.insert(spending)
            .doOnComplete {
                categoryInteractor.getCategory(spending.categoryId).doOnSuccess {
                    categoryInteractor.insert(it.copy(usageCount = it.usageCount.plus(1))).subscribe()
                }.subscribe()
            }
    }

    fun getIncomesAndSpendings(): Single<Pair<List<Spending>, List<Spending>>> {
        return spendingRepository.getAll()
            .async()
            .map { list ->
                val income = list.toMutableList().filter { !it.isSpending }
                val spending = list.toMutableList().filter { it.isSpending }
                Pair(income, spending)
            }
    }

    fun observeSpendingWithType(): Flowable<MutableList<SpendingListItem>> {
        return Flowables.combineLatest(
            observePeriods(),
            categoryInteractor.observeCategories(),
            categoryInteractor.observeSubcategories()
        )
            .map { (spendings, categories, subcategories) ->
                val muList = mutableListOf<SpendingListItem>()
                for (s in spendings) {
                    val categoryId = categories.firstOrNull { it.id == s.categoryId }
                    val subcat = subcategories.firstOrNull { it.id == s.subcategoryId }
                    muList.add(SpendingListItem(s, categoryId, subcat))
                }
                return@map muList
            }
    }

    fun observePeriods(): Flowable<List<Spending>> {
        return Flowables.combineLatest(periodsRepository.observePeriod(), spendingRepository.observeSpending())
            .map { (period, list) ->
                Log.d("M_SpendingInteractor", "listcount ${list.count()}")
                Log.d("M_SpendingInteractor", "left border ${period.leftBorder}  right border ${period.rightBorder}")
                if (period.leftBorder == period.rightBorder) {
                    list.filter { it.createdDate == period.leftBorder }
                } else {
                    list.filter { it.createdDate >= period.leftBorder && it.createdDate <= period.rightBorder }
                }
            }
    }

    fun delete(spending: Spending): Single<Disposable> {
        when (spending.isSpending) {
            false -> {
                return sumPerDayRepository.getBoth()
                    .async()
                    .map { sums ->
                        val today = sums.first.sum
                        val average = sums.second.sum
                        val deltaAverage = spending.sum / settingRepository.getTillEnd()
                        sumPerDayRepository.insertToday(today - deltaAverage).complete()
                        sumPerDayRepository.insertAverage(average - deltaAverage).complete()
                    }
                    .also {
                        spendingRepository.delete(spending).complete()
                    }
            }
            else -> {
                when (spending.createdDate.dayOfYear()) {
                    DateTime.now().dayOfYear() -> {
                        return sumPerDayRepository.getToday()
                            .async()
                            .doOnError { t -> Log.d("qwerty", t.toString()) }
                            .map { sumPerDayRepository.insertToday(it.inc(spending.sum).sum).complete() }
                            .also {
                                spendingRepository.delete(spending).complete()
                            }
                    }
                    else -> {
                        return sumPerDayRepository.getBoth()
                            .async()
                            .map { sums ->
                                val today = sums.first.sum
                                val average = sums.second.sum
                                val deltaAverage = spending.sum / settingRepository.getTillEnd()
                                sumPerDayRepository.insertToday(today + deltaAverage).complete()
                                sumPerDayRepository.insertAverage(average + deltaAverage).complete()
                            }
                            .also {
                                spendingRepository.delete(spending).complete()
                            }
                    }
                }
            }
        }
    }

    fun deleteAll(): Completable {
        return spendingRepository.deleteAll().async()
    }

}