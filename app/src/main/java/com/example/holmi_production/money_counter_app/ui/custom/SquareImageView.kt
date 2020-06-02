package com.example.holmi_production.money_counter_app.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView

class SquareImageView : androidx.appcompat.widget.AppCompatImageView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val size = if (measuredWidth >= measuredHeight) measuredHeight  else measuredWidth
        setMeasuredDimension(size, size)
    }
}