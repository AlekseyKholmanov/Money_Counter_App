package com.example.holmi_production.money_counter_app.main

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.ButtonTypes
import com.example.holmi_production.money_counter_app.model.CategoryType
import kotlinx.android.synthetic.main.keyboard.view.*
import kotlinx.android.synthetic.main.keyboard_category_date_block.view.*

class Keyboard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : RelativeLayout(context, attrs, defStyle, defStyleRes) {

    private lateinit var mKeyboardListener: IKeyboardListener
    var sum = "0"

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
        key_category.setOnClickListener { pressed(ButtonTypes.CATEGORY) }
        key_category.findViewById<TextView>(R.id.mainText).text = CategoryType.OTHER.description
        key_category.setBackgroundColor(CategoryType.OTHER.color)
        key_date.setOnClickListener { pressed(ButtonTypes.DATE) }
        key_date.findViewById<TextView>(R.id.mainText).text = "Дата"
        key_date.findViewById<TextView>(R.id.furtherText). text = "выбрать"
        expense.text = sum
    }

    fun pressed(type: ButtonTypes, value: String? = null) {
        when (type) {
            ButtonTypes.DELETE -> {
                if (sum == "0") return
                else {
                    sum = sum.dropLast(1)
                    if (sum.takeLast(1) == ".") {
                        sum = sum.dropLast(1)
                    }
                    if (sum.isEmpty())
                        sum = "0"
                }

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
                    "0" -> return
                    else -> sum += value
                }
            }
            ButtonTypes.ENTER -> {
                when (sum) {
                    "0" -> return
                    else -> {
                        mKeyboardListener.enterPressed(sum.toDouble())
                        sum = "0"
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
            ButtonTypes.DATE -> {
                mKeyboardListener.showDateDialog()
            }
            ButtonTypes.CATEGORY->{
                mKeyboardListener.showCategoryDialog()
            }
        }

        expense.text = sum
        mKeyboardListener.moneyUpdated(sum.toDouble())
    }

    fun setListener(mKeyboardListener: IKeyboardListener) {
        this.mKeyboardListener = mKeyboardListener
    }
}

interface IKeyboardListener {
    fun enterPressed(money: Double)
    fun moneyUpdated(money: Double)
    fun showCategoryDialog()
    fun showDateDialog()
}
