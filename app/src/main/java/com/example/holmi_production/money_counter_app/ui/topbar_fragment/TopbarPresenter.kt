package com.example.holmi_production.money_counter_app.ui.topbar_fragment

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.example.holmi_production.money_counter_app.extensions.*
import com.example.holmi_production.money_counter_app.model.PeriodTypeEnums
import com.example.holmi_production.money_counter_app.model.PeriodTypeEnums.*
import com.example.holmi_production.money_counter_app.model.entity.FilterPeriods
import com.example.holmi_production.money_counter_app.mvp.BasePresenter
import com.example.holmi_production.money_counter_app.storage.PeriodsRepository
import com.example.holmi_production.money_counter_app.storage.SettingRepository
import org.joda.time.DateTime
import org.joda.time.Duration
import javax.inject.Inject

@InjectViewState
class TopbarPresenter @Inject constructor(
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
                val filterPeriods = FilterPeriods("", left)

                viewState.showDate(getPeriodText(filterPeriods, CUSTOM))
                periodsRepository.insert(filterPeriods).async().subscribe().keep()
            })
            .keep()
    }

    fun setPeriod(left: DateTime, right: DateTime) {
        val fPeriod = FilterPeriods("", left, right)
        periodsRepository.insert(fPeriod).async().subscribe {
            viewState.showDate(getPeriodText(fPeriod, CUSTOM))
        }.keep()
    }

    fun setPeriod(type: PeriodTypeEnums) {
        val period = FilterPeriods("")
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
        oldPeriod: FilterPeriods,
        isRightDirection: Boolean,
        currentPeriod: PeriodTypeEnums): FilterPeriods {
        if (currentPeriod == MONTH) {
            val date =
                if (isRightDirection) oldPeriod.leftBorder.getDateWithNextMonth() else oldPeriod.leftBorder.getDateWithPreviousMonth()
            return FilterPeriods(
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
            return FilterPeriods(
                "",
                oldPeriod.leftBorder.plusDays(difDays.toInt()).withTimeAtStartOfDay(),
                oldPeriod.rightBorder.plusDays(difDays.toInt()).withTimeAtEndOfDay()
            )
        }
    }

    private fun getPeriodText(period: FilterPeriods, type: PeriodTypeEnums): String {
        return if (type == DAY) period.leftBorder.toRUformat() else
            " ${period.leftBorder.toRUformat()} - ${period.rightBorder.toRUformat()}"
    }
}