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
import android.widget.TextView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.CategoryType
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.keyboard.*
import org.joda.time.DateTime

class MainFragment : AndroidXMvpAppCompatFragment(), MainFragmnetView,
    IKeyboardListener, IScrollCallback, ICategoryPickedListener, IDatePickerCallback {
    override fun showSnack(message: String) {

        Snackbar.make(frament_main, message, Snackbar.LENGTH_SHORT)
            .show()
    }

    override fun datePicked(date: DateTime) {
        presenter.recalculateAverageSum(date)
    }

    override fun categoryPicked(type: CategoryType) {
        key_category.setBackgroundColor(type.color)
        key_category.findViewById<TextView>(R.id.mainText).text = type.description
        presenter.setType(type.id)
    }

    override fun showCategoryDialog() {
        val dialog = CategoryAlertDialog.newInstance()
        dialog.setListener(this)
        dialog.show(childFragmentManager, "category")
    }

    override fun showAverageSum(sum: String, isDisplayed: Boolean) {
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
            presenter.alarmTriggered()
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
        left_days.setOnClickListener {
            val dialog = TimePickerDialog.newInstance(withMinDate = true)
            dialog.setListener(this)
            dialog.show(childFragmentManager, "datePicker")
        }
        presenter.startObserveSum()
        presenter.initializeNotification()
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

