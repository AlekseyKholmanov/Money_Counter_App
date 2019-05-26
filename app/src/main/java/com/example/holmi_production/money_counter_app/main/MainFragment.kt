package com.example.holmi_production.money_counter_app.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.ButtonTypes
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : AndroidXMvpAppCompatFragment(), MainFragmnetView,
    IKeyboardListener {
    override fun updateMoney(money: String) {
        expense.text = money
    }

    override fun zeroPressed(number: String) {
        presenter.buttonPressed(ButtonTypes.ZERO, number)
    }

    override fun dividerPressed(divider: String) {
        presenter.buttonPressed(ButtonTypes.DIVIDER, divider)
    }

    override fun enterPressed() {
        presenter.buttonPressed(ButtonTypes.ENTER)
        Snackbar.make(main_fragment, "save", Snackbar.LENGTH_SHORT).show()
    }

    override fun deletePressed() {
        presenter.buttonPressed(ButtonTypes.DELETE)
    }

    override fun numericPressed(numeric: String) {
        presenter.buttonPressed(ButtonTypes.NUMERIC, numeric)
    }

    private lateinit var key: Keyboard

    @InjectPresenter
    lateinit var presenter: MainFragmentPresenter

    fun initPresenter(): MainFragmentPresenter {
        return App.component.getMainPresenter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        key = view.findViewById(R.id.keyboard)
        key.setListener(this)
    }
}
