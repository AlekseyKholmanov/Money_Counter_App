package com.example.holmi_production.money_counter_app.interactor

import com.example.holmi_production.money_counter_app.extensions.async
import com.example.holmi_production.money_counter_app.model.CategoryType
import com.example.holmi_production.money_counter_app.model.Spending
import com.example.holmi_production.money_counter_app.notification.NotificationManager
import com.example.holmi_production.money_counter_app.storage.SpendingRepository
import com.example.holmi_production.money_counter_app.storage.SumPerDayRepository
import io.reactivex.Single
import javax.inject.Inject

class SpendingInteractor @Inject constructor(
    private val spendingRepository: SpendingRepository,
    private val sumPerDayRepository: SumPerDayRepository) {

    fun getAllSeparated(): Single<Pair<List<Spending>, List<Spending>>> {
        return spendingRepository.getAll()
            .async()
            .map { list->
                val income = list.toMutableList().filter { it.categoryTypes==CategoryType.SALARY }
                val spending = list.toMutableList().filter { it.categoryTypes!=CategoryType.SALARY}
                Pair(income,spending)
                }
    }

    fun getAll(): Single<List<Spending>>{
        return spendingRepository.getAll()
            .async()
    }
}