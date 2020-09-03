package com.example.holmi_production.money_counter_app.ui._old_presenters

import com.example.holmi_production.money_counter_app.storage.AppPreference
import com.example.holmi_production.money_counter_app.storage.impl.TransactionDatabaseImpl
import com.example.holmi_production.money_counter_app.storage.impl.SumPerDayDatabaseImpl
import org.joda.time.DateTime


class FirstLaunchPresenter (
    private val spendingDatabase: TransactionDatabaseImpl,
    private val sumPerDayDatabase: SumPerDayDatabaseImpl,
    private val appPreference: AppPreference)
 {

    private var sum: Double = 0.0
    private var dif: Int = 0
    private lateinit var endPeriod: DateTime
    private lateinit var today: DateTime
    private var sumPerDay: Double = 0.0

    fun getSum(sum: Double) {
        this.sum = sum
    }

    fun getSumPerDay() {
//        sumPerDay = sum / dif
//        viewState.showSumPerDay(sumPerDay.toCurencyFormat())
    }

    fun updateDate(date: DateTime) {
//        endPeriod = date.withTimeAtStartOfDay()
//        today = DateTime().withTimeAtStartOfDay()
//        dif = (Days.daysBetween(today, endPeriod)).days
//        viewState.showDate(date.toRUformat(), (dif).getDayAddition())
    }

    fun goToMainScreen() {
//        spendingRepository.insert(
//            SpendingEntity(
//                DateTime(),
//                sum,
//                0,
//                null,
//                SpDirection.INCOME,
//                ""
//            )
//        ).async()
//            .subscribe({},{ Log.d("qwerty",it.message)})
//            .keep()
//        sumPerDayRepository.insertAverage(sumPerDay).complete().keep()
//        sumPerDayRepository.insertToday(sumPerDay).complete().keep()
//        settingRepository.setAppOpened()
////        settingRepository.saveStartDate(today)
////        settingRepository.saveEndDate(endPeriod)
//        viewState.showMainScreen()
    }
}
