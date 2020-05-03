package com.example.holmi_production.money_counter_app.ui.presenters

import android.util.Log
import moxy.InjectViewState
import com.example.holmi_production.money_counter_app.extensions.*
import com.example.holmi_production.money_counter_app.model.PeriodTypeEnums
import com.example.holmi_production.money_counter_app.model.PeriodTypeEnums.*
import com.example.holmi_production.money_counter_app.model.entity.FilterPeriodEntity
import com.example.holmi_production.money_counter_app.mvp.BasePresenter
import com.example.holmi_production.money_counter_app.storage.PeriodsRepository
import com.example.holmi_production.money_counter_app.storage.SettingRepository
import org.joda.time.DateTime
import org.joda.time.Duration
import javax.inject.Inject

@InjectViewState
class TopbarPresenter (
    private val periodsRepository: PeriodsRepository,
    private val settingRepository: SettingRepository) :
    BasePresenter<TopbarView>() {

    fun setNewPeriod(isRightDirection: Boolean) {
        val currentPeriod = values()[settingRepository.getPeriodType()]
        periodsRepository.getPeriod()
            .async()
            .map {
                getNewPeriod(it, isRightDirection, currentPeriod)
            }
            .subscribe({ newPeriod ->
                viewState.showDate(getPeriodText(newPeriod, currentPeriod))
                periodsRepository.insert(newPeriod).async().subscribe().keep()
            }, {
                val left = DateTime().withTimeAtStartOfDay().minusDays(7)
                val filterPeriods = FilterPeriodEntity("", left)

                viewState.showDate(getPeriodText(filterPeriods, CUSTOM))
                periodsRepository.insert(filterPeriods).async().subscribe().keep()
            })
            .keep()
    }

    fun setPeriod(left: DateTime, right: DateTime) {
        val fPeriod = FilterPeriodEntity("", left, right)
        periodsRepository.insert(fPeriod).async().subscribe {
            viewState.showDate(getPeriodText(fPeriod, CUSTOM))
        }.keep()
    }

    fun setPeriod(type: PeriodTypeEnums) {
        val period = FilterPeriodEntity("")
        when (type) {
            DAY -> {
            }
            WEEK -> {
                period.leftBorder = period.leftBorder.withDayOfWeek(1)
                period.rightBorder = period.rightBorder.withDayOfWeek(7)
            }
            MONTH -> {
                period.leftBorder = period.leftBorder.withDayOfMonth(1)
                period.rightBorder = period.rightBorder.withTimeAtEndOfMonth(DateTime().monthOfYear)
            }
            CUSTOM -> {
                return
            }
        }
        periodsRepository.insert(period).async().subscribe {
            viewState.showDate(getPeriodText(period, type))
        }.keep()
        settingRepository.setPeriodType(type.id)
    }

    fun getPeriod() {
        val type = values()[settingRepository.getPeriodType()]
        periodsRepository.getPeriod()
            .async()
            .subscribe({ period ->
                viewState.showDate(getPeriodText(period, type))
            }) {
                Log.d("M_TopbarPresenter", "get period error \r\n blah \r\n ${it.localizedMessage}")
            }
            .keep()
    }

    private fun getNewPeriod(
        oldPeriod: FilterPeriodEntity,
        isRightDirection: Boolean,
        currentPeriod: PeriodTypeEnums): FilterPeriodEntity {
        if (currentPeriod == MONTH) {
            val date =
                if (isRightDirection) oldPeriod.leftBorder.withNextMonthDate() else oldPeriod.leftBorder.withPreviousMonthDate()
            return FilterPeriodEntity(
                "",
                date.withTimeAtStartOfMonth().withTimeAtStartOfDay(),
                date.withTimeAtEndOfMonth(date.monthOfYear).withTimeAtEndOfDay()
            )
        } else {
            val difDays = if (isRightDirection)
                Duration(
                    oldPeriod.leftBorder,
                    oldPeriod.rightBorder
                ).standardDays + 1 //чтобы не пересекались даты
            else
                Duration(oldPeriod.rightBorder, oldPeriod.leftBorder).standardDays - 1
            return FilterPeriodEntity(
                "",
                oldPeriod.leftBorder.plusDays(difDays.toInt()).withTimeAtStartOfDay(),
                oldPeriod.rightBorder.plusDays(difDays.toInt()).withTimeAtEndOfDay()
            )
        }
    }

    private fun getPeriodText(period: FilterPeriodEntity, type: PeriodTypeEnums): String {
        return if (type == DAY) period.leftBorder.toRUformat() else
            " ${period.leftBorder.toRUformat()} - ${period.rightBorder.toRUformat()}"
    }
}