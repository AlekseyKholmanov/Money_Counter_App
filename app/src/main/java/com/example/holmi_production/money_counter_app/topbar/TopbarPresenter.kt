package com.example.holmi_production.money_counter_app.topbar

import com.arellomobile.mvp.InjectViewState
import com.example.holmi_production.money_counter_app.extensions.async
import com.example.holmi_production.money_counter_app.model.FilterPeriods
import com.example.holmi_production.money_counter_app.mvp.BasePresenter
import com.example.holmi_production.money_counter_app.storage.PeriodsRepository
import org.joda.time.DateTime
import org.joda.time.Duration
import java.time.LocalDateTime
import javax.inject.Inject

@InjectViewState
class TopbarPresenter @Inject constructor(private val periodsRepository: PeriodsRepository) :
    BasePresenter<TopbarView>() {

    fun setNewPeriod(isRightDirection: Boolean) {
        periodsRepository.getPeriod()
            .async()
            .map {
                getNewPeriod(it, isRightDirection)
            }
            .subscribe({ newPeriod ->
                viewState.showDate(newPeriod.leftBorder, newPeriod.rightBorder)
                periodsRepository.insert(newPeriod).async().subscribe().keep()
            }, {
                val period = FilterPeriods("", DateTime().minusDays(6), DateTime())
                val newPeriod = getNewPeriod(period, isRightDirection)
                viewState.showDate(newPeriod.leftBorder, newPeriod.rightBorder)
                periodsRepository.insert(newPeriod).async().subscribe().keep()
            })
            .keep()
    }

    fun getPeriod() {
        periodsRepository.getPeriod()
            .async()
            .subscribe({ period ->
                viewState.showDate(period.leftBorder, period.rightBorder)
            }) {
                viewState.showDate(DateTime().withTimeAtStartOfDay().minusDays(6), DateTime().withTimeAtStartOfDay())
            }
            .keep()
    }

    private fun getNewPeriod(oldPeriod: FilterPeriods, isRightDirection: Boolean): FilterPeriods {
        val difDays = if (isRightDirection)
            Duration(oldPeriod.leftBorder, oldPeriod.rightBorder).standardDays + 1 //чтобы не пересекались даты
        else
            Duration(oldPeriod.rightBorder, oldPeriod.leftBorder).standardDays - 1
        return FilterPeriods(
            "",
            oldPeriod.leftBorder.plusDays(difDays.toInt()).withTimeAtStartOfDay(),
            oldPeriod.rightBorder.plusDays(difDays.toInt()).withTime(23,59,59,59)
        )

    }
}