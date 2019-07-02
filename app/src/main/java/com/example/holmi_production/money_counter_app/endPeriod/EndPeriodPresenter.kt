package com.example.holmi_production.money_counter_app.endPeriod

import com.arellomobile.mvp.InjectViewState
import com.example.holmi_production.money_counter_app.extensions.toCurencyFormat
import com.example.holmi_production.money_counter_app.extensions.toDateTime
import com.example.holmi_production.money_counter_app.extensions.toRUformat
import com.example.holmi_production.money_counter_app.interactor.SpendingInteractor
import com.example.holmi_production.money_counter_app.mvp.BasePresenter
import com.example.holmi_production.money_counter_app.storage.SettingRepository
import javax.inject.Inject
@InjectViewState
class EndPeriodPresenter @Inject constructor(
    private val settingRepository: SettingRepository,
    private val spendingInteractor: SpendingInteractor
):BasePresenter<EndPeriodView>() {

    fun getSum(){
        val startDate = settingRepository.getStartDate().toDateTime()
        val endDate = settingRepository.getEndPeriod().toDateTime()
        val periodDays = settingRepository.getTillEnd()
        spendingInteractor.getAllSeparate()
            .subscribe ({ it ->
                val income = it.first
                val spending = it.second
                val leftSum = income.sumByDouble { it.sum } - spending.sumByDouble { it.sum }
                val spendForPeriod = spending.filter { it.spendingDate>=startDate }.sumByDouble { it.sum }
                val averageRealSum = spendForPeriod/periodDays
                viewState.showLeftSum(leftSum.toCurencyFormat())
                viewState.showSpendedSum(spendForPeriod.toCurencyFormat())
                viewState.showDatePeriod(startDate.toRUformat(),endDate.toRUformat())
                viewState.ShowAverageSumForPeriod(averageRealSum.toCurencyFormat())

            },{})
            .keep()
    }

    fun goToMain(){
        viewState.goToMain()
        settingRepository.setIsEnd(false)
    }

}