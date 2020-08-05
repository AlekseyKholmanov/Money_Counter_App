package com.example.holmi_production.money_counter_app.ui.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import com.example.holmi_production.money_counter_app.ui.utils.dpToPx

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
class Indicator @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paintColors = mutableListOf<@ColorInt Int>()

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG).apply {
        color = Color.TRANSPARENT
        style = Paint.Style.FILL
    }

    private var partHeight = 0f
    private var viewHeight = 0
    private var viewWidth = 0

    private val path = Path()
    private val rectF = RectF()

    fun setColors(colors: List<Int>) {
        paintColors.clear()
        colors.forEach { color ->
            paintColors.add(color)
        }
        partHeight = viewHeight / paintColors.size.toFloat()
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        paintColors.forEachIndexed { index, color ->
            if(paintColors.size == 1) {
                drawSingle(canvas, color)
            } else {
                when (index) {
                    0 -> drawTop(canvas, color)
                    paintColors.size - 1 -> drawBottom(canvas, color)
                    else -> drawMiddle(canvas, index, color)
                }
            }
        }
    }

    private fun drawSingle(canvas: Canvas, @ColorInt color: Int) {
        path.reset()
        paint.color = color
        rectF.set(0f, 0f, width.toFloat(), partHeight)
        path.addRoundRect(
            rectF,
            floatArrayOf(width.toFloat(), width.toFloat(),  0f, 0f, 0f, 0f,  width.toFloat(), width.toFloat()),
            Path.Direction.CW
        )
        canvas.drawPath(path, paint)
    }

    private fun drawTop(canvas: Canvas, @ColorInt color: Int) {
        path.reset()
        paint.color = color
        rectF.set(0f, 0f, width.toFloat() - 2* dpToPx(2), partHeight)
        path.addRoundRect(
            rectF,
            floatArrayOf(0f, 0f, width.toFloat(), width.toFloat(), 0f, 0f, 0f, 0f),
            Path.Direction.CW
        )
        canvas.drawPath(path, paint)
    }

    private fun drawMiddle(
        canvas: Canvas,
        index: Int,
        @ColorInt color: Int
    ) {
        paint.color = color
        canvas.drawRect(
            0f,
            partHeight * index,
            viewWidth.toFloat(),
            partHeight + (partHeight * index),
            paint
        )
    }

    private fun drawBottom(canvas: Canvas, @ColorInt color: Int) {
        path.reset()
        paint.color = color
        rectF.set(0f, height - partHeight, width.toFloat(), height.toFloat())
        path.addRoundRect(
            rectF,
            floatArrayOf(0f, 0f, 0f, 0f, width.toFloat(), width.toFloat(), 0f, 0f),
            Path.Direction.CW
        )
        canvas.drawPath(path, paint)

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewHeight = h
        viewWidth = w
        partHeight = viewHeight / paintColors.size.toFloat()
    }

}