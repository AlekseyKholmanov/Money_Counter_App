package com.example.holmi_production.money_counter_app.ui.presenters

import androidx.lifecycle.ViewModel
import com.example.holmi_production.money_counter_app.extensions.*
import com.example.holmi_production.money_counter_app.model.enums.PeriodType
import com.example.holmi_production.money_counter_app.model.enums.PeriodType.*
import com.example.holmi_production.money_counter_app.model.entity.FilterPeriodEntity
import com.example.holmi_production.money_counter_app.storage.impl.PeriodsDatabaseImpl
import com.example.holmi_production.money_counter_app.storage.AppPreference

import org.joda.time.DateTime

class TopbarViewModel (
    private val periodsDatabase: PeriodsDatabaseImpl,
    private val appPreference: AppPreference) :
    ViewModel() {

    fun setNewPeriod(isRightDirection: Boolean) {
//        val currentPeriod = values()[settingRepository.getPeriodType()]
//        periodsRepository.getPeriod()
//            .async()
//            .map {
//                getNewPeriod(it, isRightDirection, currentPeriod)
//            }
//            .subscribe({ newPeriod ->
//                viewState.showDate(getPeriodText(newPeriod, currentPeriod))
//                periodsRepository.insert(newPeriod).async().subscribe().keep()
//            }, {
//                val left = DateTime().withTimeAtStartOfDay().minusDays(7)
//                val filterPeriods = FilterPeriodEntity("", left)
//
//                viewState.showDate(getPeriodText(filterPeriods, CUSTOM))
//                periodsRepository.insert(filterPeriods).async().subscribe().keep()
//            })
//            .keep()
    }

    fun setPeriod(left: DateTime, right: DateTime) {
//        val fPeriod = FilterPeriodEntity("", left, right)
//        periodsRepository.insert(fPeriod).async().subscribe {
//            viewState.showDate(getPeriodText(fPeriod, CUSTOM))
//        }.keep()
    }

    fun setPeriod(type: PeriodType) {
        val period = FilterPeriodEntity("")
        when (type) {
            DAY -> {
            }
            WEEK -> {
                period.from = period.from.withDayOfWeek(1)
                period.to = period.to.withDayOfWeek(7)
            }
            MONTH -> {
                period.from = period.from.withDayOfMonth(1)
                period.to = period.to.withTimeAtEndOfMonth()
            }
            CUSTOM -> {
                return
            }
        }
//        periodsRepository.insert(period).async().subscribe {
//            viewState.showDate(getPeriodText(period, type))
//        }.keep()
//        settingRepository.setPeriodType(type.id)
    }

    fun getPeriod() {
//        val type = values()[settingRepository.getPeriodType()]
//        periodsRepository.getPeriod()
//            .async()
//            .subscribe({ period ->
//                viewState.showDate(getPeriodText(period, type))
//            }) {
//                Log.d("M_TopbarPresenter", "get period error \r\n blah \r\n ${it.localizedMessage}")
//            }
//            .keep()
    }

}