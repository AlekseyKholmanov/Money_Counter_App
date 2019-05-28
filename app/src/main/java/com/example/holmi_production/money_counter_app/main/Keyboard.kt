package com.example.holmi_production.money_counter_app.main

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.ButtonTypes
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
        key_0.setOnClickListener { mKeyboardListener.buttonPressed( ButtonTypes.ZERO,"0") }
        key_000.setOnClickListener { mKeyboardListener.buttonPressed(ButtonTypes.ZERO,"000") }
        key_1.setOnClickListener { mKeyboardListener.buttonPressed(ButtonTypes.NUMERIC,"1") }
        key_2.setOnClickListener { mKeyboardListener.buttonPressed(ButtonTypes.NUMERIC,"2") }
        key_3.setOnClickListener { mKeyboardListener.buttonPressed(ButtonTypes.NUMERIC,"3") }
        key_4.setOnClickListener { mKeyboardListener.buttonPressed(ButtonTypes.NUMERIC,"4") }
        key_5.setOnClickListener { mKeyboardListener.buttonPressed(ButtonTypes.NUMERIC,"5") }
        key_6.setOnClickListener { mKeyboardListener.buttonPressed(ButtonTypes.NUMERIC,"6") }
        key_7.setOnClickListener { mKeyboardListener.buttonPressed(ButtonTypes.NUMERIC,"7") }
        key_8.setOnClickListener { mKeyboardListener.buttonPressed(ButtonTypes.NUMERIC,"8") }
        key_9.setOnClickListener { mKeyboardListener.buttonPressed(ButtonTypes.NUMERIC,"9") }
        key_divider.setOnClickListener { mKeyboardListener.buttonPressed(ButtonTypes.DIVIDER,".") }
        key_delete.setOnClickListener { mKeyboardListener.buttonPressed(ButtonTypes.DELETE) }
        key_enter.setOnClickListener { mKeyboardListener.buttonPressed(ButtonTypes.ENTER) }
    }

    fun setListener(mKeyboardListener: IKeyboardListener) {
        this.mKeyboardListener = mKeyboardListener
    }
}

interface IKeyboardListener {
    fun buttonPressed(type:ButtonTypes, value: String? = null)
}
