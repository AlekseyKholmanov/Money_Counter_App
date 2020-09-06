package com.example.holmi_production.money_counter_app.ui.viewModels

import androidx.lifecycle.ViewModel
import com.example.holmi_production.money_counter_app.interactor.SpendingInteractor
import com.example.holmi_production.money_counter_app.storage.db.TransactionDatabase
import com.example.holmi_production.money_counter_app.storage.db.SumPerDayDatabase


/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
class KeyboardViewModel(
    private val sumPerDayDatabase: SumPerDayDatabase,
    private val spendingInteractor: SpendingInteractor,
    private val transactionDatabase: TransactionDatabase
) : ViewModel() {


}