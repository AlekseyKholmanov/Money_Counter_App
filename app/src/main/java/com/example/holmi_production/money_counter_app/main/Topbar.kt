package com.example.holmi_production.money_counter_app.main

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.example.holmi_production.money_counter_app.R
import kotlinx.android.synthetic.main.topbar.view.*

class Topbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {
    init {
        View.inflate(context, R.layout.topbar, this)
        topbar_left.setOnClickListener {
            mTopbarListener?.leftPressed()
            topbar_date.text = "leftPressed"
        }
        topbar_right.setOnClickListener {
            mTopbarListener?.rightPressed()
            topbar_date.text = "rightPressed"
        }
        topbar_date.setOnClickListener {
            mTopbarListener?.chooseDatePressed()
        }
    }

    var mTopbarListener: ITopbarListener? = null

    fun setListener(mTopbarListener: ITopbarListener?) {
        this.mTopbarListener = mTopbarListener
    }
}

interface ITopbarListener {
    fun leftPressed()
    fun rightPressed()
    fun chooseDatePressed()
}