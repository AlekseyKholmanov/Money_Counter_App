package com.example.holmi_production.money_counter_app.main

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.example.holmi_production.money_counter_app.R
import kotlinx.android.synthetic.main.horizontal_scroll_item.view.*

class ScrollItem @JvmOverloads constructor(
    type: String,
    position: Int,
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private lateinit var mScrollListener: IScrollListener

    init {
        View.inflate(getContext(), R.layout.horizontal_scroll_item, this)
        scroll_item_text.text = type
        scroll_item_text.tag = position
        if (position==0)
            scroll_item_text.setBackgroundColor(Color.DKGRAY)
        else
            scroll_item_text.setBackgroundColor(Color.LTGRAY)
        scroll_item_text.setOnClickListener {
            mScrollListener.buttonPressed(position)
        }
    }

    fun setListener(mScrollListener: IScrollListener) {
        this.mScrollListener = mScrollListener
    }

    interface IScrollListener {
        fun buttonPressed(position: Int)
    }
}