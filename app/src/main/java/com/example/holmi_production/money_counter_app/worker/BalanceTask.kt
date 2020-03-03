package com.example.holmi_production.money_counter_app.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.holmi_production.money_counter_app.extensions.async
import com.example.holmi_production.money_counter_app.model.SpDirection
import com.example.holmi_production.money_counter_app.model.entity.Balance
import com.example.holmi_production.money_counter_app.storage.BalanceRepository
import com.example.holmi_production.money_counter_app.storage.SpendingRepository
import org.joda.time.DateTime

class BalanceTask constructor(
    private val context: Context,
    private val params: WorkerParameters
) : Worker(context, params) {
    lateinit var spendingRepository: SpendingRepository
    lateinit var balanceRepository: BalanceRepository

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
            .doAfterTerminate {
                val balance = Balance(DateTime().withTimeAtStartOfDay(), todayBalance)
                balanceRepository.insert(balance)
                    .subscribe()
                Log.d("M_SaveBalanceTask", "wnd work $todayBalance")
            }
            .async()
            .subscribe()
        return Result.success()
    }

}