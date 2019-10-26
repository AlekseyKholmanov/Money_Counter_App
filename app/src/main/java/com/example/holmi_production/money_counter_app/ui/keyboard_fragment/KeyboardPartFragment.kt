package com.example.holmi_production.money_counter_app.ui.keyboard_fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.Vibrator
import com.example.holmi_production.money_counter_app.extensions.hideKeyboard
import com.example.holmi_production.money_counter_app.model.ButtonTypes
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment
import kotlinx.android.synthetic.main.keyboard.*
import javax.inject.Inject

class KeyboardPartFragment : AndroidXMvpAppCompatFragment() {
    companion object {
        fun newInstance(): KeyboardPartFragment {
            return KeyboardPartFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.keyboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        key_spending.setOnClickListener { pressed(ButtonTypes.ENTER_UP) }
        key_income.setOnClickListener { pressed(ButtonTypes.ENTER_DOWN) }
        key_category.setOnClickListener { pressed(ButtonTypes.CATEGORY) }
        comment.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                comment.clearFocus()
                comment.hideKeyboard()
                Log.d("qwerty", "ledt")
            } else {
                Log.d("qwerty", "has")
            }
        }
        purshace_sum_textview.text = purshaseSum
        App.component.inject(this)
    }

    fun setListener(mKeyboardListener: IKeyboardListener?) {
        this.mKeyboardListener = mKeyboardListener
    }

    fun updateCategoryButton(color: Int?, imageId: Int?) {
        key_category.setBackgroundColor(color ?: Color.TRANSPARENT)
        key_category.findViewById<ImageView>(R.id.categoryImage)
            .setImageResource(imageId ?: R.drawable.ic_launcher_foreground)
    }

    private fun pressed(type: ButtonTypes, value: String? = null) {
        vibrator.vibrate(50)

        when (type) {
            ButtonTypes.DELETE -> {
                if (purshaseSum == "0") return
                else {
                    purshaseSum = purshaseSum.dropLast(1)
                    if (purshaseSum.takeLast(1) == ".") {
                        purshaseSum = purshaseSum.dropLast(1)
                    }
                    if (purshaseSum.isEmpty())
                        purshaseSum = "0"
                }
            }
            ButtonTypes.DIVIDER -> {
                when {
                    value == "." && purshaseSum.contains(".") -> return
                    purshaseSum == "" -> purshaseSum = "0."
                    else -> purshaseSum += value
                }
            }
            ButtonTypes.ZERO -> {
                if (purshaseSum == "0") return
                if (purshaseSum.contains(Regex("[.].*"))) return
                else purshaseSum += value

            }
            ButtonTypes.ENTER_UP -> {
                enterPressed(true)
            }
            ButtonTypes.ENTER_DOWN -> {
                enterPressed(false)
            }
            ButtonTypes.NUMERIC -> {
                if (purshaseSum == "0")
                    purshaseSum = ""
                if (purshaseSum.contains('.') && purshaseSum.takeLast(1) != ".")
                    purshaseSum = purshaseSum.dropLast(1)
                purshaseSum += value
            }
            ButtonTypes.CATEGORY -> {
                mKeyboardListener!!.showCategoryDialog()
            }
        }

        purshace_sum_textview.text = purshaseSum
        mKeyboardListener!!.moneyUpdated(purshaseSum.toDouble())
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("M_KeyboardPart", "onDestroy")
        super.onDestroy()
    }

    private fun enterPressed(isSpending: Boolean) {
        when (purshaseSum) {
            "0" -> return
            else -> {
                val text = comment.text.toString()
                mKeyboardListener!!.enterPressed(purshaseSum.toDouble(), text, isSpending)
                purshaseSum = "0"
                clearCommentField()
            }
        }
    }

    private fun clearCommentField() {
        comment.setText("")
    }

    @Inject
    lateinit var vibrator:Vibrator
    private var purshaseSum = "0"
    private var mKeyboardListener: IKeyboardListener? = null
}

interface IKeyboardListener {
    fun enterPressed(money: Double, comment: String, isSpending:Boolean)
    fun moneyUpdated(money: Double)
    fun showCategoryDialog()
}