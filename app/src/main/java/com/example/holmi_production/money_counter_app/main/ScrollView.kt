package com.example.holmi_production.money_counter_app.main

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.Display
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import com.example.holmi_production.money_counter_app.R
import kotlinx.android.synthetic.main.main_fragment.view.*

class ScrollView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, activity:Activity
) : HorizontalScrollView(context, attrs, defStyleAttr) {
    init {
        View.inflate(context, R.layout.horizontal_scroll_view,this)
        val display = activity.windowManager.defaultDisplay
        val mWidth = display.width
        val viewWidth = mWidth/3
        val lParams = ViewGroup.LayoutParams(viewWidth,ViewGroup.LayoutParams.WRAP_CONTENT)
        hsv.layoutParams = lParams


    }
}