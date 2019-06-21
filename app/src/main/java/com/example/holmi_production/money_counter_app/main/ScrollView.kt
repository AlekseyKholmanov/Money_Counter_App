package com.example.holmi_production.money_counter_app.main

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.CategoryType
import kotlinx.android.synthetic.main.horizontal_scroll.view.*

class ScrollView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : HorizontalScrollView(context, attrs, defStyleAttr), ScrollItem.IScrollListener {

    private var pressed: Int = 0
    private lateinit var mScrollCallback: IScrollCallback
    override fun buttonPressed(position: Int) {
        if (position == pressed)
            return
        else {
            mScrollCallback.callback(position)
            val child = hsv_container.getChildAt(pressed)
            child.findViewById<Button>(R.id.scroll_item_text).setBackgroundColor(Color.LTGRAY)
            val newsChild = hsv_container.getChildAt(position)
            newsChild.findViewById<Button>(R.id.scroll_item_text).setBackgroundColor(Color.DKGRAY)
            pressed = position
        }
    }

    init {
        val rootView = View.inflate(getContext(), R.layout.horizontal_scroll, this)
        val container = rootView.findViewById<LinearLayout>(R.id.hsv_container)
        val count = CategoryType.values().size - 1
        for (i in 0..count) {
            val category = CategoryType.values()[i].description
            val view = ScrollItem(category, i, getContext())
            view.setListener(this)
            container.addView(view)
        }
    }

    fun setCallback(mScrollCallback: IScrollCallback) {
        this.mScrollCallback = mScrollCallback
    }
}

interface IScrollCallback {
    fun callback(type: Int)
}