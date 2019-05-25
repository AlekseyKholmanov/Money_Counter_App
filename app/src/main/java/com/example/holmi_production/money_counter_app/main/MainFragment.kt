package com.example.holmi_production.money_counter_app.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : AndroidXMvpAppCompatFragment(),MainFragmnetView,
    IKeyboardListener {
    override fun updateMoney(money: String) {
        expense.text = money
    }

    override fun zeroPressed(number: String) {
        if (text == "")
            return
        else {
            text += number
            displaySum()
        }
    }

    override fun dividerPressed(divider: String) {
        if (divider == "." && text.contains("."))
            return
        else {
            text += divider
            displaySum()
        }
    }

    override fun enterPressed() {
        presenter.saveSpending()
        Snackbar.make(main_fragment, "save", Snackbar.LENGTH_SHORT).show()
    }

    override fun deletePressed() {
        text = text.dropLast(1)
        if (text.takeLast(1) == ".")
            text = text.dropLast(1)
        displaySum()
    }

    override fun numberPressed(simbol: String) {
        if (text.contains('.') && text.takeLast(1) != ".")
            text = text.dropLast(1)
        text += simbol
        displaySum()
    }

    private fun displaySum() {

    }

    private var text: String = ""
    private lateinit var key: Keyboard

    @InjectPresenter
    lateinit var presenter: MainFragmentPresenter

    fun initPresenter():MainFragmentPresenter{
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
