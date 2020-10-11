package com.example.holmi_production.money_counter_app.ui.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.ui.utils.dpToPx
import kotlin.math.abs
import kotlin.math.min

class CustomPieChart @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyle, defStyleRes) {

    private val numberSize: Int = 22
    private val spendingTypeSize: Int = 15

    private val textColor: Int = ContextCompat.getColor(context, R.color.black_translucent_38)
    private val numberColor: Int = ContextCompat.getColor(context, R.color.black)

    private val startAngle = -90f
    private var deltaAngle = 0f

    private var chartLineWidth = dpToPx(5)
    private val borderWidth = dpToPx(1)
    private val paddingSize = dpToPx(1)
    private val borderLineColor = ContextCompat.getColor(context, R.color.white)


    private var _data = listOf<CharItem>()
    private var _spendingTypeText = ""
    private var _totalSumText = ""

    private val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeWidth = chartLineWidth
        style = Paint.Style.STROKE
    }
    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = borderLineColor
        strokeWidth = borderWidth
        style = Paint.Style.STROKE
    }
    private val innerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = borderLineColor
        style = Paint.Style.FILL
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = textColor
        typeface = Typeface.DEFAULT_BOLD
        textSize = dpToPx(spendingTypeSize)
    }

    private var numberPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = numberColor
        typeface = Typeface.DEFAULT_BOLD
        textSize = dpToPx(numberSize)
    }

//    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
//        color = zeroSizeColor
//        style = Paint.Style.FILL
//        textSize = dpToPx(numberSize)
//        textAlign = Paint.Align.CENTER
//    }

    private val rect = RectF()
    private val textBounds = Rect()
    private val numberTextBounds = Rect()

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
            isDrawText = typedArray.getBoolean(R.styleable.CustomPieChart_isDrawText, false)
            typedArray.recycle()
        }
        circlePaint.strokeWidth = chartLineWidth
    }
    var isDrawText: Boolean = false

    fun updateIndicator(
        items: List<CharItem>,
        spendingTypeText: String = "",
        totalSumText: Double = 0.0
    ) {
        deltaAngle = 0f
        _data = items
        _spendingTypeText = "${spendingTypeText}▼"
        _totalSumText = abs(totalSumText).toString()
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
                drawArc(canvas, it.color, it.angle - 0.5f)
                drawArc(canvas, Color.WHITE, 0.5f)
            }
        }
        canvas.drawCircle(cx, cy, outerRadius, borderPaint)
        canvas.drawCircle(cx, cy, innerRadius, innerPaint)
        if (isDrawText) {
            drawText(canvas)
        }
    }

    private fun drawArc(c: Canvas, color: Int, angle: Float) {
        circlePaint.color = color
        c.drawArc(rect, startAngle + deltaAngle, angle, false, circlePaint)
        deltaAngle += angle
    }

    private fun drawText(c: Canvas, ) {
        textPaint.getTextBounds(_spendingTypeText, 0, _spendingTypeText.length, textBounds)
        numberPaint.getTextBounds(_totalSumText, 0, _totalSumText.length, numberTextBounds)
        val size = min(measuredWidth, measuredHeight)
        val backgroundBounds = RectF()
            .apply {
                left = chartLineWidth / 2 + borderWidth + paddingSize
                top = chartLineWidth / 2 + borderWidth + paddingSize
                right = size - chartLineWidth / 2 - borderWidth - paddingSize
                bottom = size - chartLineWidth / 2 - borderWidth - paddingSize
            }
        val textY = backgroundBounds.centerY() - textBounds.height()/2 - dpToPx(6)
        val numberY = backgroundBounds.centerY() + numberTextBounds.height()/2+ dpToPx(6)
        c.drawText(
            _spendingTypeText,
            backgroundBounds.centerX() - textBounds.width() / 2,
            textY,
            textPaint
        )
        c.drawText(
            _totalSumText,
            backgroundBounds.centerX() - numberTextBounds.width() / 2,
            numberY,
            numberPaint)
    }
}

class CharItem(
    val angle: Float,
    val color: Int
)