package com.example.holmi_production.money_counter_app.storage

import android.content.SharedPreferences
import com.example.holmi_production.money_counter_app.async
import com.example.holmi_production.money_counter_app.subscribeOnIo
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject
import org.joda.time.DateTime
import javax.inject.Inject

class SettingRepository @Inject constructor(private val pref: SharedPreferences){
    private val settingSubject = PublishSubject.create<DateTime>()

    fun setAppOpened(){
        pref.edit().putBoolean(FIRST_OPEN,true).apply()
    }

    fun saveStartDate(startDay: DateTime){
        pref.edit().putLong(START_PERIOD, startDay.millis).apply()
    }

    fun saveEndDate(endDate: DateTime){
        pref.edit().putLong(END_PERIOD,endDate.millis).apply()
        settingSubject.onNext(endDate)
    }

    fun isOpened():Boolean{
        return pref.contains(FIRST_OPEN)
    }

    fun observeEndDate():Flowable<DateTime>{
        return settingSubject.toFlowable(BackpressureStrategy.LATEST).subscribeOnIo()
    }


    val FIRST_OPEN = "FirstOpen"
    val START_PERIOD = "START_PERIOD"
    val END_PERIOD = "END_PERIOD"
}