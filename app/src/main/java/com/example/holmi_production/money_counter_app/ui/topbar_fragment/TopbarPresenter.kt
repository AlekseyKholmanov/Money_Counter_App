package com.example.holmi_production.money_counter_app.ui.topbar_fragment

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.example.holmi_production.money_counter_app.extensions.async
import com.example.holmi_production.money_counter_app.model.entity.FilterPeriods
import com.example.holmi_production.money_counter_app.mvp.BasePresenter
import com.example.holmi_production.money_counter_app.storage.PeriodsRepository
import org.joda.time.DateTime
import org.joda.time.Duration
import org.xml.sax.DTDHandler
import java.lang.NullPointerException
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
                Log.d("M_TopbarPresenter", "set period error \r\n ${it.message} ")

            })
            .keep()
    }

    fun setPeriod(left:DateTime, right:DateTime) {
        val fPeriod = FilterPeriods("", left, right)
        periodsRepository.insert(fPeriod).async().subscribe {
            viewState.showDate(left,right)
        }.keep()
    }

    fun getPeriod() {
        periodsRepository.getPeriod()
            .async()
            .subscribe({ period ->
                viewState.showDate(period.leftBorder, period.rightBorder)
            }) {
                Log.d("M_TopbarPresenter", "get period error \r\n blah \r\n ${it.localizedMessage}")
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
            oldPeriod.rightBorder.plusDays(difDays.toInt()).withTime(23, 59, 59, 59)
        )

    }
}