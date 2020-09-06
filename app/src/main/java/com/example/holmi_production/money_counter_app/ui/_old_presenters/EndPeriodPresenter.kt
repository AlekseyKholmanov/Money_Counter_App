package com.example.holmi_production.money_counter_app.ui._old_presenters


import com.example.holmi_production.money_counter_app.interactor.SpendingInteractor
import com.example.holmi_production.money_counter_app.storage.db.AppPreference


class EndPeriodPresenter (
    private val appPreference: AppPreference,
    private val spendingInteractor: SpendingInteractor
)  {
//TODO переделать
    fun getSum() {
//        val startDate = settingRepository.getStartDate().toDateTime()
//        val endDate = settingRepository.getEndPeriod().toDateTime()
//        val periodDays = settingRepository.getTillEnd()
//        spendingInteractor.getIncomesAndSpendings()
//            .subscribe({ it ->
//                val income = it.first
//                val spending = it.second
//                val leftSum = income.sumByDouble { it.sum } - spending.sumByDouble { it.sum }
//                val spendForPeriod = spending.filter { it.createdDate >= startDate }.sumByDouble { it.sum }
//                val averageRealSum = spendForPeriod / periodDays
//                viewState.showLeftSum(leftSum.toCurencyFormat())
//                viewState.showSpendedSum(spendForPeriod.toCurencyFormat())
//                viewState.showDatePeriod(startDate.toRUformat(), endDate.toRUformat())
//                viewState.ShowAverageSumForPeriod(averageRealSum.toCurencyFormat())
//
//            }, {
//                Log.d("qwerty", it.message)
//            })
//            .keep()
    }
//
//    fun goToMain() {
////        settingRepository.setIsEnd(false)
//        viewState.goToMain()
//    }

}