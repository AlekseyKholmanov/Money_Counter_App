package com.example.holmi_production.money_counter_app.ui.custom

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.ui.utils.dpToPx

class CurrencyBadge (
    private val context: Context
) : Drawable(){

    private var _char: Char = ' '

    private var numberSize: Int = 10

    private val width = dpToPx(16)
    private val height = dpToPx(16)
    private val strokeWidthPx = dpToPx(1)
    private val cx = width / 2
    private val cy = height / 2

    private val _color = ContextCompat.getColor(context, R.color.black)
    private val backgroundColor = ContextCompat.getColor(context, R.color.white)

    private val circleBackgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = _color
        strokeWidth = strokeWidthPx
        style = Paint.Style.STROKE
        isAntiAlias = true
    }

    private val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = backgroundColor
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = _color
        style = Paint.Style.FILL
        textSize = dpToPx(numberSize)
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
    }

    private val rect = RectF()
    private val textBounds = Rect()


    fun updateCurrency(currency: Char) {
        _char = currency
        invalidateSelf()
    }

    override fun draw(c: Canvas) {
        drawInnerCircle(c)
        drawOuterCircle(c)
        drawCurrency(c, _char)
    }

    private fun drawOuterCircle(c: Canvas) {
        rect.apply {
            left = strokeWidthPx
            top = strokeWidthPx
            right = width - strokeWidthPx
            bottom = height - strokeWidthPx
        }
        c.drawCircle(cx, cy, width / 2 - 2*strokeWidthPx, circleBackgroundPaint)
    }


    private fun drawInnerCircle(c: Canvas) {
        rect.apply {
            left = 0f
            top = 0f
            right = width
            bottom = height
        }
        c.drawCircle(cx, cy, width / 2, circlePaint)
    }

    private fun drawCurrency(c: Canvas, currency: Char) {
        textPaint.getTextBounds(currency.toString(), 0, 1, textBounds)
        val backgroundBounds = RectF()
            .apply {
                left = strokeWidthPx
                top = strokeWidthPx
                right = width - strokeWidthPx
                bottom = height - strokeWidthPx
            }

        val textBottom = backgroundBounds.centerY() - textBounds.exactCenterY()
        val cy = textBottom

        c.drawText(currency.toString(), cx, cy, textPaint)
    }

    override fun setAlpha(alpha: Int) {
        circlePaint.alpha = alpha
        textPaint.alpha = alpha
    }

    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT

    override fun setColorFilter(colorFilter: ColorFilter?) {
        circlePaint.colorFilter = colorFilter
        textPaint.colorFilter = colorFilter
    }
}