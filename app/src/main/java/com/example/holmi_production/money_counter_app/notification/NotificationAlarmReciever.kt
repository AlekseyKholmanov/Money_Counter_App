package com.example.holmi_production.money_counter_app.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.extensions.async
import com.example.holmi_production.money_counter_app.extensions.toRUformat
import com.example.holmi_production.money_counter_app.interactor.BalanceInteractor
import com.example.holmi_production.money_counter_app.interactor.NotificationInteractor
import com.example.holmi_production.money_counter_app.interactor.SpendingInteractor
import com.example.holmi_production.money_counter_app.worker.BalancePopulateTask
import com.example.holmi_production.money_counter_app.worker.NotificationTask
import com.example.holmi_production.money_counter_app.model.SpDirection
import com.example.holmi_production.money_counter_app.model.entity.Balance
import com.example.holmi_production.money_counter_app.storage.SettingRepository
import dagger.android.AndroidInjection
import javax.inject.Inject

class NotificationAlarmReciever : BroadcastReceiver() {


    @Inject
    lateinit var settingRepository: SettingRepository
    @Inject
    lateinit var notificationInteractor: NotificationInteractor
    @Inject
    lateinit var balanceInteractor: BalanceInteractor
    @Inject
    lateinit var spendingInteractor:SpendingInteractor

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("M_NotAlarmRecivr", "recieve notification intent")
        if (intent.action == NotificationTask.ACTION) {
            notificationInteractor.alarmTriggered()
        }
        if(intent.action == BalancePopulateTask.ACTION){
            settingRepository.setBalancePopulated()
            spendingInteractor.getAll()
                .map { it.groupBy { it.createdDate.withTimeAtStartOfDay() }.toSortedMap(Comparator { o1, o2 -> o1.compareTo(o2) }) }
                .async()
                .subscribe { list->
                    var daylyBalance = 0.0
                    list.forEach { (t, u) ->
                        u.forEach {
                            if(it.isSpending == SpDirection.INCOME) daylyBalance+= it.sum else daylyBalance-=it.sum
                        }
                        val balance = Balance(t,daylyBalance)
                        balanceInteractor.insert(balance)
                        Log.d("M_NotAlarmReciever","${t.toRUformat()} $daylyBalance")
                    }
                }
            
            
        }
    }
}
