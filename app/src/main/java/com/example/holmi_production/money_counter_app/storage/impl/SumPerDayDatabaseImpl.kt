package com.example.holmi_production.money_counter_app.storage.impl

import com.example.holmi_production.money_counter_app.model.entity.SumPerDayEntity
import com.example.holmi_production.money_counter_app.orm.ExpenseDatabase
import com.example.holmi_production.money_counter_app.storage.SumPerDayDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext


class SumPerDayDatabaseImpl(
    private val database: ExpenseDatabase
) : SumPerDayDatabase {
    companion object {
        const val TODAY = "TODAY_SUM"
        const val AVERAGE = "AVERAGE_SUM"
    }

    private val dao = database.sumPerDayDao()

    suspend fun insertToday(todaySum: Double) {
        withContext(Dispatchers.IO) {
            val sum = SumPerDayEntity(TODAY, todaySum)
            dao.insert(sum)
        }
    }

    suspend fun insertAverage(averageSum: Double) {
        withContext(Dispatchers.IO) {
            val sum = SumPerDayEntity(AVERAGE, averageSum)
            dao.insert(sum)
        }
    }

    fun observeToday(): Flow<SumPerDayEntity> = dao.observeSum(TODAY)

    fun observeAverage(): Flow<SumPerDayEntity> = dao.observeSum(AVERAGE)


//    fun getToday(): Single<SumPerDayEntity> {
//        return Single.fromCallable { dao.getSum(TODAY) }
//    }
//
//    fun getTodayAndAverage(): Single<Pair<SumPerDayEntity, SumPerDayEntity>> {
//        return Singles.zip(getToday(), getAverage())
//    }
//
//    fun getAverage(): Single<SumPerDayEntity> {
//        return Single.fromCallable { dao.getSum(AVERAGE) }
//    }
//
}