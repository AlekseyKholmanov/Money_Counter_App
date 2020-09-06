package com.example.holmi_production.money_counter_app.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.holmi_production.money_counter_app.storage.db.impl.BalanceDatabaseImpl
import com.example.holmi_production.money_counter_app.storage.db.impl.TransactionDatabaseImpl

class BalanceTask constructor(
    private val context: Context,
    private val params: WorkerParameters
) : Worker(context, params) {
    lateinit var spendingDatabase: TransactionDatabaseImpl
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