package com.example.holmi_production.money_counter_app.main

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.example.holmi_production.money_counter_app.R
import kotlinx.android.synthetic.main.scroll_view_item.view.*

class ScrollItem @JvmOverloads constructor(
    type: String,
    position: Int,
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private lateinit var mScrollListener: IScrollListener
    private var isClicked: Boolean = false

    init {
        View.inflate(getContext(), R.layout.scroll_view_item, this)
        scroll_item_text.text = type
        scroll_item_text.tag = position
        scroll_item_text.setOnClickListener {
            isClicked = !isClicked
            it.setBackgroundColor(
                if (isClicked)
                    Color.LTGRAY
                else
                    Color.TRANSPARENT
            )
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