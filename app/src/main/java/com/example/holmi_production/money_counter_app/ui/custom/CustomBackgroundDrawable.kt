package com.example.holmi_production.money_counter_app.ui.custom

import android.graphics.*
import android.graphics.drawable.Drawable
import kotlin.random.Random

class CustomBackgroundDrawable(private val width: Int, private val height: Int) : Drawable() {

    private val colors = listOf(
        Color.parseColor("#D90368"),
        Color.parseColor("#04A777"),
        Color.parseColor("#FDC133"),
        Color.parseColor("#820263")
    )
    private var colorList: List<Int> = listOf()
    val path = Path()

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private val startAreaX: Float
    private val startAreaY: Float
    private val startAreaStart: Float
    private val startAreaEnd: Float
    private val middleAreaX: Float
    private val middleAreaY: Float
    private val middleAreaStart: Float
    private val middleAreaEnd: Float
    private val endAreaX: Float
    private val endAreaY: Float
    private val endAreaStart: Float
    private val endAreaEnd: Float

    init {
        colorList = colors.shuffled()

        startAreaX = Random.nextInt(0, width).toFloat()
        startAreaY = Random.nextInt(height * 3 / 4, height).toFloat()
        startAreaStart = Random.nextInt(height * 3 / 4, height * 3 / 2).toFloat()
        startAreaEnd = Random.nextInt(height / 2, height).toFloat()

        middleAreaX = Random.nextInt(0, width).toFloat()
        middleAreaY = Random.nextInt(0, height * 3 / 4).toFloat()
        middleAreaStart = Random.nextInt(height / 4, height * 3 / 4).toFloat()
        middleAreaEnd = Random.nextInt(height / 4, height * 3 / 4).toFloat()

        endAreaX = Random.nextInt(0, width).toFloat()
        endAreaY = Random.nextInt(0 - height * 3 / 4, height / 2).toFloat()
        endAreaStart = Random.nextInt(0, height / 2).toFloat()
        endAreaEnd = Random.nextInt(0, height / 2).toFloat()
    }

    override fun draw(canvas: Canvas) {
        paint.color = colorList.last()
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
        drawStart(width, height)
        canvas.drawPath(path, paint)
        path.reset()
        drawMiddle(width, height)
        canvas.drawPath(path, paint)
        path.reset()
        drawEnd(width, height)
        canvas.drawPath(path, paint)
        path.reset()
    }

    private fun drawStart(width: Int, height: Int) {
        paint.color = colorList[2]
        path.moveTo(0f, startAreaStart)
        path.quadTo(startAreaX, startAreaY, width.toFloat(), startAreaEnd)
        path.lineTo(width.toFloat(), 0f)
        path.lineTo(0f, 0f)
        path.lineTo(0f, startAreaStart)
    }

    private fun drawMiddle(width: Int, height: Int) {
        paint.color = colorList[1]
        path.moveTo(0f, middleAreaStart)
        path.quadTo(middleAreaX, middleAreaY, width.toFloat(), middleAreaEnd)
        path.lineTo(width.toFloat(), 0f)
        path.lineTo(0f, 0f)
        path.lineTo(0f, middleAreaStart)
    }

    private fun drawEnd(width: Int, height: Int) {
        paint.color = colorList[0]

        path.moveTo(0f, endAreaStart)
        path.quadTo(endAreaX, endAreaY, width.toFloat(), endAreaEnd)
        path.lineTo(width.toFloat(), 0f)
        path.lineTo(0f, 0f)
        path.lineTo(0f, endAreaStart)

    }

    override fun setAlpha(alpha: Int) {
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
    }

    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT
}