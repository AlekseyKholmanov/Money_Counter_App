package com.example.holmi_production.money_counter_app.ui.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.ui.utils.dpToPx
import kotlin.math.min

class CustomPieChart @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyle, defStyleRes) {

    private var numberSize: Int = 13
    private val startAngle = -90f
    private var deltaAngle = 0f
    private var chartLineWidth = dpToPx(5)
    private val borderWidth = dpToPx(1)
    private val paddingSize = dpToPx(1)
    private val borderLineColor = ContextCompat.getColor(context, R.color.white)
    private var _data = listOf<CharItem>()

    private val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeWidth = chartLineWidth
        style = Paint.Style.STROKE
    }
    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = borderLineColor
        strokeWidth = borderWidth
        style = Paint.Style.STROKE
    }
//    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
//        color = zeroSizeColor
//        style = Paint.Style.FILL
//        textSize = dpToPx(numberSize)
//        textAlign = Paint.Align.CENTER
//    }

    private val rect = RectF()
    private val textBounds = Rect()

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(
                it,
                R.styleable.CustomPieChart,
                defStyle,
                defStyleRes
            )
            chartLineWidth = typedArray.getDimensionPixelOffset(
                R.styleable.CustomPieChart_borderWidth,
                dpToPx(1).toInt()
            ).toFloat()
            typedArray.recycle()
        }
        circlePaint.strokeWidth = chartLineWidth
    }

    fun updateIndicator(
        items: List<CharItem>
    ) {
        deltaAngle = 0f
        _data = items
        invalidate()
    }


    override fun onDraw(canvas: Canvas) {

        val size = min(measuredWidth, measuredHeight)
        rect.apply {
            left = chartLineWidth / 2 + borderWidth + paddingSize
            top = chartLineWidth / 2 + borderWidth + paddingSize
            right = size - chartLineWidth / 2 - borderWidth - paddingSize
            bottom = size - chartLineWidth / 2 - borderWidth - paddingSize
        }
        val cx = (rect.right - rect.left) / 2 + chartLineWidth / 2 + 2 * borderWidth
        val cy = (rect.bottom - rect.top) / 2 + chartLineWidth / 2 + 2 * borderWidth
        val outerRadius = (rect.right - rect.left) / 2 + chartLineWidth / 2
        val innerRadius = (rect.right - rect.left) / 2 - chartLineWidth / 2
        if (_data.isEmpty()) {
            drawArc(canvas, ContextCompat.getColor(context, R.color.colorPrimaryDark), 360f)
        } else {
            _data.forEach {
                drawArc(canvas, it.color, it.angle-0.5f)
                drawArc(canvas, Color.WHITE, 0.5f)
            }
        }
        canvas.drawCircle(cx, cy, outerRadius, borderPaint)
        canvas.drawCircle(cx, cy, innerRadius, borderPaint)
    }

    private fun drawArc(c: Canvas, color: Int, angle: Float) {
        circlePaint.color = color
        c.drawArc(rect, startAngle + deltaAngle, angle, false, circlePaint)
        deltaAngle += angle
    }

//    private fun drawNumber(c: Canvas, text: String) {
//        textPaint.getTextBounds(text, 0, text.length, textBounds)
//        val backgroundBounds = RectF()
//            .apply {
//                left = borderWidth
//                top = borderWidth
//                right = width - borderWidth
//                bottom = height - borderWidth
//            }
//        val textBottom = backgroundBounds.centerY() - textBounds.exactCenterY()
//        c.drawText(text, backgroundBounds.centerX(), textBottom, textPaint)
//    }
}

class CharItem(
    val angle: Float,
    val color: Int
)