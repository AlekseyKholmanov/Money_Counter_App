package com.example.holmi_production.money_counter_app.main

import com.example.holmi_production.money_counter_app.async
import com.example.holmi_production.money_counter_app.model.CategoryTypes
import com.example.holmi_production.money_counter_app.model.Spending
import com.example.holmi_production.money_counter_app.mvp.BasePresenter
import com.example.holmi_production.money_counter_app.storage.SpendingRepository
import com.example.holmi_production.money_counter_app.toCurencyFormat
import java.util.*
import javax.inject.Inject

class MainFragmentPresenter @Inject constructor(private val repository: SpendingRepository) :
    BasePresenter<MainFragmnetView>() {

    var sum = ""

    fun saveSpending() {
        val spending = Spending(null, sum.toFloat(), CategoryTypes.NONE, Date())
        repository.insert(spending)
            .async()
            .subscribe {
                viewState.updateMoney(sum.toCurencyFormat())
                sum = ""
            }
            .keep()
    }
}