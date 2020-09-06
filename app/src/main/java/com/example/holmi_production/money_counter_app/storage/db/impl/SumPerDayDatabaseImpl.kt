package com.example.holmi_production.money_counter_app.storage.db.impl

import com.example.holmi_production.money_counter_app.model.entity.SumPerDayEntity
import com.example.holmi_production.money_counter_app.orm.SumPerDayDao
import com.example.holmi_production.money_counter_app.storage.db.SumPerDayDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext


class SumPerDayDatabaseImpl(
    private val dao: SumPerDayDao
) : SumPerDayDatabase {
    companion object {
        const val TODAY = "TODAY_SUM"
        const val AVERAGE = "AVERAGE_SUM"
    }

    override suspend fun insertToday(todaySum: Double) {
        withContext(Dispatchers.IO) {
            val sum = SumPerDayEntity(TODAY, todaySum)
            dao.insert(sum)
        }
    }

    override suspend fun insertAverage(averageSum: Double) {
        withContext(Dispatchers.IO) {
            val sum = SumPerDayEntity(AVERAGE, averageSum)
            dao.insert(sum)
        }
    }

    override fun observeToday(): Flow<SumPerDayEntity> = dao.observeSum(TODAY)

    override fun observeAverage(): Flow<SumPerDayEntity> = dao.observeSum(AVERAGE)


    override suspend fun getToday(): SumPerDayEntity {
        return dao.getSum(TODAY)
    }

    override suspend fun getAverage(): SumPerDayEntity {
        return dao.getSum(AVERAGE)
    }

}