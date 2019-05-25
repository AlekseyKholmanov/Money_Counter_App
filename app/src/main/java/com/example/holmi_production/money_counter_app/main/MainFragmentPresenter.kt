package com.example.holmi_production.money_counter_app.main

import com.example.holmi_production.money_counter_app.mvp.BasePresenter
import com.example.holmi_production.money_counter_app.storage.SpendingRepository
import javax.inject.Inject

class MainFragmentPresenter @Inject constructor(private val repository: SpendingRepository) :
    BasePresenter<MainFragmnetView>() {

}