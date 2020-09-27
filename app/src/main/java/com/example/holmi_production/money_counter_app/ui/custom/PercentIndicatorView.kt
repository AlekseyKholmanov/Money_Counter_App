package com.example.holmi_production.money_counter_app.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.example.holmi_production.money_counter_app.R

class PercentIndicatorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyle, defStyleRes) {

    private val lineColor = ContextCompat.getColor(context, R.color.yellowAccent)
    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = lineColor
        style = Paint.Style.FILL_AND_STROKE
    }
    private var _percent: Double = 0.0
    private val rect = RectF()

    fun setLinePercentage(percent: Double) {
        _percent = percent
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        val drawingLength = _percent * measuredWidth / 100
        Log.d(
            "M_PercentIndicatorView",
            "$drawingLength measure:$measuredWidth width:$width height:$height measureHeight:$measuredHeight"
        )
        rect.apply {
            left = 0f
            top = 0f
            right = drawingLength.toFloat()
            bottom = height()
        }
        canvas.drawRect(0f, 0f, drawingLength.toFloat(), height.toFloat(), linePaint)
    }
}