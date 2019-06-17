package com.example.holmi_production.money_counter_app.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.NotificationManager
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

class MainFragment : AndroidXMvpAppCompatFragment(), MainFragmnetView,
    IKeyboardListener, IScrollCallback{

    override fun showNewSumPerDay(sum: String, isDisplayed: Boolean) {
        val displayed = if (isDisplayed) View.VISIBLE else View.GONE
        new_sum_per_day_text.visibility = displayed
        new_sum_per_day.visibility = displayed
        new_sum_per_day.text = sum
    }

    override fun enterPressed(money: Double) {
        Log.d("qwerty", money.toString())
        presenter.saveSpend(money)
    }

    override fun moneyUpdated(money: Double) {
    }

    override fun showDaysLeft(days: String) {
        left_days.text = days
    }

    override fun showSumPerDay(money: String) {
        sum_per_day.text = money
    }

    override fun showIncomeSum(money: String) {
        left.text = money
    }

    override fun showSpentSum(sum: String) {
        spent.text = sum
    }

    override fun callback(type: Int) {
        presenter.setType(type)
    }

    override fun showMoney(money: String) {
//        expense.text = money
    }

    private lateinit var key: Keyboard
    private lateinit var scroll: ScrollView
    private val mDayChangedReciever: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d("qwerty", "recieved")
        }

    }
    @InjectPresenter
    lateinit var presenter: MainFragmentPresenter

    @ProvidePresenter
    fun initPresenter(): MainFragmentPresenter {
        return App.component.getMainPresenter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        key = view.findViewById(R.id.keyboard)
        scroll = view.findViewById(R.id.hsv)
        scroll.setCallback(this)
        key.setListener(this)
        presenter.getSum()
        presenter.getDaysLeft()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LocalBroadcastManager.getInstance(this.requireContext())
            .registerReceiver(mDayChangedReciever, IntentFilter("custom-intent-filter"))
    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(this.requireContext()).unregisterReceiver(mDayChangedReciever)
        super.onDestroy()
    }
}

