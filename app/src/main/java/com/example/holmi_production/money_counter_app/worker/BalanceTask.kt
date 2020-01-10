package com.example.holmi_production.money_counter_app.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.holmi_production.money_counter_app.model.SpDirection
import com.example.holmi_production.money_counter_app.model.entity.Balance
import com.example.holmi_production.money_counter_app.orm.ExpenseDatabase
import org.joda.time.DateTime

class BalanceTask(context: Context, params: WorkerParameters) : Worker(context, params) {

    companion object {
        const val ACTION = "SAVE_TODAY_BALANCE"
    }

    override fun doWork(): Result {
        Log.d("BalanceTask", "start work")
        val instance = ExpenseDatabase.getInstance(applicationContext)
        val spendings = instance!!.spendingDao.getSpendings()
        var todayBalance = 0.0
        spendings.forEach {
            if (it.isSpending == SpDirection.SPENDING) {
                todayBalance -= it.sum
            } else {
                todayBalance += it.sum
            }
        }
        instance.balanceDao.insert(Balance(DateTime().withTimeAtStartOfDay(), todayBalance))
        WorkerManager.startBalanceWorker()
        Log.d("M_SaveBalanceTask", "wnd work $todayBalance")
        return Result.success()
    }
}