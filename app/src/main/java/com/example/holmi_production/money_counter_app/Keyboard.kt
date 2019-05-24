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
        key_0.setOnClickListener { mKeyboardListener.numberPressed(0) }
        key_000.setOnClickListener { mKeyboardListener.numberPressed(100) }
    }

    fun setListener(mKeyboardListener: IKeyboardListener){
        this.mKeyboardListener = mKeyboardListener
    }

}
interface IKeyboardListener {
    fun numberPressed(number:Int)
}
