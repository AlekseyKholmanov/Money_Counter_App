package com.example.holmi_production.money_counter_app

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.numbers_keyboard.view.*

class Keyboard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : RelativeLayout(context, attrs, defStyle, defStyleRes) {

    private lateinit var mKeyboardListener: IKeyboardListener

    init {
        Log.d("qwerty", "init")
        View.inflate(context, R.layout.numbers_keyboard, this)
        key_0.setOnClickListener { mKeyboardListener.numberPressed("0") }
        key_000.setOnClickListener { mKeyboardListener.numberPressed("000") }
        key_1.setOnClickListener { mKeyboardListener.numberPressed("1") }
        key_2.setOnClickListener { mKeyboardListener.numberPressed("2") }
        key_3.setOnClickListener { mKeyboardListener.numberPressed("3") }
        key_4.setOnClickListener { mKeyboardListener.numberPressed("4") }
        key_5.setOnClickListener { mKeyboardListener.numberPressed("5") }
        key_6.setOnClickListener { mKeyboardListener.numberPressed("6") }
        key_7.setOnClickListener { mKeyboardListener.numberPressed("7") }
        key_8.setOnClickListener { mKeyboardListener.numberPressed("8") }
        key_9.setOnClickListener { mKeyboardListener.numberPressed("9") }
        key_divider.setOnClickListener { mKeyboardListener.numberPressed(".") }
        key_delete.setOnClickListener { mKeyboardListener.deletePressed() }
        key_enter.setOnClickListener { mKeyboardListener.enterPressed() }
    }

    fun setListener(mKeyboardListener: IKeyboardListener) {
        this.mKeyboardListener = mKeyboardListener
    }

}

interface IKeyboardListener {
    fun numberPressed(number: String)
    fun enterPressed()
    fun deletePressed()
}
