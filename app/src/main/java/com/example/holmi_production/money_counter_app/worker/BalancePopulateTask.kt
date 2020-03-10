package com.example.holmi_production.money_counter_app.worker

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.holmi_production.money_counter_app.extensions.async
import com.example.holmi_production.money_counter_app.extensions.toRUformat
import com.example.holmi_production.money_counter_app.interactor.BalanceInteractor
import com.example.holmi_production.money_counter_app.interactor.SpendingInteractor
import com.example.holmi_production.money_counter_app.model.SpDirection
import com.example.holmi_production.money_counter_app.model.entity.Balance
import com.example.holmi_production.money_counter_app.notification.NotificationAlarmReciever

class BalancePopulateTask(context: Context, params: WorkerParameters) : Worker(context, params) {
    companion object {
        const val ACTION = "POPULATION_WORK"
    }

    lateinit var spendingInteractor: SpendingInteractor
    lateinit var balanceInteractor: BalanceInteractor
    //TODO
    @SuppressLint("CheckResult")
    override fun doWork(): Result {
        spendingInteractor.getAll()
            .map {
                it.groupBy { it.createdDate.withTimeAtStartOfDay() }
                    .toSortedMap(Comparator { o1, o2 -> o1.compareTo(o2) })
            }
            .async()
            .subscribe { list ->
                //TODO перенести в бэкграунд работы и подготовить данные заранее, после в doOnCOmplete заинсертить их
                var daylyBalance = 0.0
                list.forEach { (t, u) ->
                    u.forEach {
                        if (it.isSpending == SpDirection.INCOME) daylyBalance += it.sum else daylyBalance -= it.sum
                    }
                    val balance = Balance(t, daylyBalance)
                    balanceInteractor.insert(balance)
                    Log.d("M_NotAlarmReciever", "${t.toRUformat()} $daylyBalance")
                }
            }
        return Result.success()
    }
}