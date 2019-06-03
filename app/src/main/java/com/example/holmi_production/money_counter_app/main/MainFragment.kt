package com.example.holmi_production.money_counter_app.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.ButtonTypes
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : AndroidXMvpAppCompatFragment(), MainFragmnetView,
    IKeyboardListener, IScrollCallback {
    override fun showDaysLeft(days: String) {
        sum_per_day_left_days.text = days
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

    override fun buttonPressed(type: ButtonTypes, value: String?) {
        presenter.buttonPressed(type, value)
    }

    override fun showMoney(money: String) {
        expense.text = money
    }

    private lateinit var key: Keyboard
    private lateinit var scroll: ScrollView

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
}
