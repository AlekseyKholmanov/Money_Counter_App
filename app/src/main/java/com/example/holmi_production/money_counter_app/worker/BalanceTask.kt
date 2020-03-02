package com.example.holmi_production.money_counter_app.worker

import android.content.Context
import android.util.Log
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.holmi_production.money_counter_app.di.modules.ChildWorkerFactory
import com.example.holmi_production.money_counter_app.extensions.async
import com.example.holmi_production.money_counter_app.model.SpDirection
import com.example.holmi_production.money_counter_app.model.entity.Balance
import com.example.holmi_production.money_counter_app.storage.BalanceRepository
import com.example.holmi_production.money_counter_app.storage.SpendingRepository
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import org.joda.time.DateTime
import javax.inject.Inject
import javax.inject.Provider

class BalanceTask @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val params: WorkerParameters,
    private val spendingRepository: SpendingRepository,
    private val balanceRepository: BalanceRepository
) : Worker(context, params) {

    override fun doWork(): Result {
        Log.d("BalanceTask", "start work")
        var todayBalance = 0.0
        spendingRepository.getAll()
            .map {
                it.forEach { spending ->
                    if (spending.isSpending == SpDirection.SPENDING) {
                        todayBalance -= spending.sum
                    } else {
                        todayBalance += spending.sum
                    }
                }
            }
            .async()
            .doAfterTerminate {
                val balance = Balance(DateTime().withTimeAtStartOfDay(), todayBalance)
                balanceRepository.insert(balance)
                    .subscribe()
            }
            .subscribe()
        WorkerManager.startBalanceWorker(context)
        Log.d("M_SaveBalanceTask", "wnd work $todayBalance")
        return Result.success()
    }

    @AssistedInject.Factory
    interface Factory : ChildWorkerFactory
}