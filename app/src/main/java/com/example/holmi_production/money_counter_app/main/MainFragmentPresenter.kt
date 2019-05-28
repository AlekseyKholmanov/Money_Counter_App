package com.example.holmi_production.money_counter_app.main

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.example.holmi_production.money_counter_app.async
import com.example.holmi_production.money_counter_app.model.ButtonTypes
import com.example.holmi_production.money_counter_app.model.CategoryType
import com.example.holmi_production.money_counter_app.model.Spending
import com.example.holmi_production.money_counter_app.mvp.BasePresenter
import com.example.holmi_production.money_counter_app.storage.SpendingRepository
import com.example.holmi_production.money_counter_app.toCurencyFormat
import org.joda.time.DateTime
import javax.inject.Inject

@InjectViewState
class MainFragmentPresenter @Inject constructor(private val repository: SpendingRepository) :
    BasePresenter<MainFragmnetView>() {

    var sum = ""

    private fun saveSpending() {
        val spending = Spending(null, sum.toFloat(), CategoryType.SALARY, DateTime())
        repository.insert(spending)
            .async()
            .subscribe {
                Log.d("qwerty","inserted")
            }
            .keep()
    }

    fun buttonPressed(buttonTypes: ButtonTypes, value: String? = null) {
        when (buttonTypes) {
            ButtonTypes.DELETE -> {
                sum = sum.dropLast(1)
                if (sum.takeLast(1) == ".")
                    sum = sum.dropLast(1)
            }
            ButtonTypes.DIVIDER -> {
                when {
                    value == "." && sum.contains(".") -> return
                    else -> sum += value
                }
            }
            ButtonTypes.ZERO -> {
                when (sum) {
                    "" -> return
                    else -> sum += value
                }
            }
            ButtonTypes.ENTER -> {
                saveSpending()
                sum = ""
            }
            ButtonTypes.NUMERIC -> {
                if (sum.contains('.') && sum.takeLast(1) != ".")
                    sum = sum.dropLast(1)
                sum += value
            }
        }
        viewState.updateMoney(sum.toCurencyFormat())
    }
}