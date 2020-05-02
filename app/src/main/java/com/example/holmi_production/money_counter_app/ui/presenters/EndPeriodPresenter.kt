package com.example.holmi_production.money_counter_app.ui.presenters

import moxy.InjectViewState
import com.example.holmi_production.money_counter_app.interactor.SpendingInteractor
import com.example.holmi_production.money_counter_app.mvp.BasePresenter
import com.example.holmi_production.money_counter_app.storage.SettingRepository
import javax.inject.Inject

@InjectViewState
class EndPeriodPresenter @Inject constructor(
    private val settingRepository: SettingRepository,
    private val spendingInteractor: SpendingInteractor
) : BasePresenter<EndPeriodView>() {
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

    fun goToMain() {
//        settingRepository.setIsEnd(false)
        viewState.goToMain()
    }

}