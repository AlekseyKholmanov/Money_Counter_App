package com.example.holmi_production.money_counter_app.main

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.CategoryClass
import kotlinx.android.synthetic.main.main_fragment.view.*

class ScrollView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : HorizontalScrollView(context, attrs, defStyleAttr), ScrollItem.IScrollListener {

    override fun buttonPressed(position: Int) {
        Log.d("qwerty", CategoryClass.values()[position].name)
    }

    private lateinit var scrollItem: ScrollItem

    init {
        val rootView = View.inflate(getContext(), R.layout.horizontal_scroll_view, this)
        val container = rootView.findViewById<LinearLayout>(R.id.hsv_container)
        val lParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        hsv.layoutParams = lParams
        val count = CategoryClass.values().size - 1
        for (i in 0..count) {
            val category = CategoryClass.values()[i].name
            val view = ScrollItem(category, i, getContext())
            view.setListener(this)

            container.addView(view)
        }
    }
}