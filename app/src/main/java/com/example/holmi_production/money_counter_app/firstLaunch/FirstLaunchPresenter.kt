package com.example.holmi_production.money_counter_app.firstLaunch

import com.arellomobile.mvp.InjectViewState
import com.example.holmi_production.money_counter_app.mvp.BasePresenter
import com.example.holmi_production.money_counter_app.storage.SpendingRepository
import org.joda.time.DateTime
import javax.inject.Inject

@InjectViewState
class FirstLaunchPresenter@Inject constructor(private val repository: SpendingRepository) :
    BasePresenter<FirstLaunchView>() {

    fun saveSum(sum:Long){
        val now = DateTime.now().withTimeAtStartOfDay()
    }

}
