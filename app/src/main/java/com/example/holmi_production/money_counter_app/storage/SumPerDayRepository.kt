package com.example.holmi_production.money_counter_app.storage

import android.content.SharedPreferences
import com.example.holmi_production.money_counter_app.MainActivity
import com.example.holmi_production.money_counter_app.model.SumPerDay
import com.example.holmi_production.money_counter_app.orm.ExpenseDatabase
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import org.joda.time.DateTime
import java.util.*
import javax.inject.Inject

class SumPerDayRepository @Inject constructor(
    private val pref:SharedPreferences,
    private val database: ExpenseDatabase
) {
    private val dao = database.sumPerDayDao

    fun insert(sumPerDay: SumPerDay): Completable {
        return Completable.fromCallable { dao.insert(sumPerDay) }
    }

    fun insert(sumPerDayList: Collection<SumPerDay>): Completable {
        return Completable.fromCallable { dao.insert(sumPerDayList) }
    }

    fun observeSumPerDay(): Flowable<List<SumPerDay>> = dao.observeSumPerDay()

    fun getOnDate(dateTime: DateTime): Single<SumPerDay> {
        return Single.fromCallable { dao.getOnDate(dateTime) }
    }

    fun getFromDate(dateTime: DateTime): Single<List<SumPerDay>> {
        return Single.fromCallable { dao.getPeriodFrom(dateTime) }
    }

    fun setAppOpened(){
        pref.edit().putBoolean(FIRST_OPEN,true).apply()
    }

    fun saveStartDate(startDay:DateTime){
        pref.edit().putLong(START_PERIOD, startDay.millis).apply()
    }

    fun saveEndDate(endDate:DateTime){
        pref.edit().putLong(END_PERIOD,endDate.millis).apply()
    }

    fun isOpened():Boolean{
        return pref.contains(FIRST_OPEN)
    }



    val FIRST_OPEN = "FirstOpen"
    val START_PERIOD = "START_PERIOD"
    val END_PERIOD = "END_PERIOD"
}