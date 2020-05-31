package com.example.holmi_production.money_counter_app.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.holmi_production.money_counter_app.extensions.async
import com.example.holmi_production.money_counter_app.model.SpDirection
import com.example.holmi_production.money_counter_app.model.entity.BalanceEntity
import com.example.holmi_production.money_counter_app.storage.impl.BalanceDatabaseImpl
import com.example.holmi_production.money_counter_app.storage.impl.SpendingDatabaseImpl
import org.joda.time.DateTime

class BalanceTask constructor(
    private val context: Context,
    private val params: WorkerParameters
) : Worker(context, params) {
    lateinit var spendingDatabase: SpendingDatabaseImpl
    lateinit var balanceDatabase: BalanceDatabaseImpl

    override fun doWork(): Result {
//        Log.d("BalanceTask", "start work")
//        var todayBalance = 0.0
//        spendingDatabase.getAll()
//            .map {
//                it.forEach { spending ->
//                    if (spending.isSpending == SpDirection.SPENDING) {
//                        todayBalance -= spending.sum
//                    } else {
//                        todayBalance += spending.sum
//                    }
//                }
//            }
//            .doAfterTerminate {
//                val balance = BalanceEntity(DateTime().withTimeAtStartOfDay(), todayBalance)
//                balanceDatabase.insert(balance)
//                    .subscribe()
//                Log.d("M_SaveBalanceTask", "wnd work $todayBalance")
//            }
//            .async()
//            .subscribe()
        return Result.success()
    }

}