package com.example.holmi_production.money_counter_app.main

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.ButtonTypes
import com.example.holmi_production.money_counter_app.toCurencyFormat
import kotlinx.android.synthetic.main.keyboard.view.*

class Keyboard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : RelativeLayout(context, attrs, defStyle, defStyleRes) {

    private lateinit var mKeyboardListener: IKeyboardListener
    var sum = ""

    init {
        View.inflate(context, R.layout.keyboard, this)
        key_0.setOnClickListener { pressed(ButtonTypes.ZERO, "0") }
        key_1.setOnClickListener { pressed(ButtonTypes.NUMERIC, "1") }
        key_2.setOnClickListener { pressed(ButtonTypes.NUMERIC, "2") }
        key_3.setOnClickListener { pressed(ButtonTypes.NUMERIC, "3") }
        key_4.setOnClickListener { pressed(ButtonTypes.NUMERIC, "4") }
        key_5.setOnClickListener { pressed(ButtonTypes.NUMERIC, "5") }
        key_6.setOnClickListener { pressed(ButtonTypes.NUMERIC, "6") }
        key_7.setOnClickListener { pressed(ButtonTypes.NUMERIC, "7") }
        key_8.setOnClickListener { pressed(ButtonTypes.NUMERIC, "8") }
        key_9.setOnClickListener { pressed(ButtonTypes.NUMERIC, "9") }
        key_divider.setOnClickListener { pressed(ButtonTypes.DIVIDER, ".") }
        key_delete.setOnClickListener { pressed(ButtonTypes.DELETE) }
        key_enter.setOnClickListener { pressed(ButtonTypes.ENTER) }
    }

    fun pressed(type: ButtonTypes, value: String? = null) {
        when (type) {
            ButtonTypes.DELETE -> {
                sum = sum.dropLast(1)
                if (sum.takeLast(1) == ".")
                    sum = sum.dropLast(1)
            }
            ButtonTypes.DIVIDER -> {
                when {
                    value == "." && sum.contains(".") -> return
                    sum == "" -> sum = "0."
                    else -> sum += value
                }
            }
            ButtonTypes.ZERO -> {
                when (sum) {
                    "" -> return
                    else -> sum += value
                }
            }
            ButtonTypes.ENTER -> {
                when (sum) {
                    "" -> return
                    else -> {
                        mKeyboardListener.enterPressed(sum.toDouble())
                        sum = ""
                    }
                }
            }
            ButtonTypes.NUMERIC -> {
                if (sum == "0")
                    sum = ""
                if (sum.contains('.') && sum.takeLast(1) != ".")
                    sum = sum.dropLast(1)
                sum += value
            }
        }

        expense.text = sum
    }

    fun setListener(mKeyboardListener: IKeyboardListener) {
        this.mKeyboardListener = mKeyboardListener
    }
}

interface IKeyboardListener {
    fun enterPressed(money: Double)
    fun moneyUpdated(money: Double)
}
