package com.example.holmi_production.money_counter_app.ui.custom

import android.graphics.*
import android.graphics.drawable.Drawable
import kotlin.random.Random

class CustomBackgroundDrawable : Drawable() {

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

    override fun draw(canvas: Canvas) {
        colorList = colors.shuffled()

        val width = bounds.width()
        val height = bounds.height()
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
        val randX = Random.nextInt(0, width).toFloat()
        val randY = Random.nextInt(height * 3 / 4, height).toFloat()
        val randStart = Random.nextInt(height * 3 / 4, height * 3 / 2).toFloat()
        val randEnd = Random.nextInt(height / 2, height).toFloat()
        path.moveTo(0f, randStart)
        path.quadTo(randX, randY, width.toFloat(), randEnd)
        path.lineTo(width.toFloat(), 0f)
        path.lineTo(0f, 0f)
        path.lineTo(0f, randStart)
    }

    private fun drawMiddle(width: Int, height: Int) {

        paint.color = colorList[1]
        val randX = Random.nextInt(0, width).toFloat()
        val randY = Random.nextInt(0, height * 3 / 4).toFloat()
        val randStart = Random.nextInt(height / 4, height * 3 / 4).toFloat()
        val randEnd = Random.nextInt(height / 4, height * 3 / 4).toFloat()
        path.moveTo(0f, randStart)
        path.quadTo(randX, randY, width.toFloat(), randEnd)
        path.lineTo(width.toFloat(), 0f)
        path.lineTo(0f, 0f)
        path.lineTo(0f, randStart)
    }

    private fun drawEnd(width: Int, height: Int) {
        paint.color = colorList[0]
        val randX = Random.nextInt(0, width).toFloat()
        val randY = Random.nextInt(0 - height * 3 / 4, height / 2).toFloat()
        val randStart = Random.nextInt(0, height / 2).toFloat().toFloat()
        val randEnd = Random.nextInt(0, height / 2).toFloat()
        path.moveTo(0f, randStart)
        path.quadTo(randX, randY, width.toFloat(), randEnd)
        path.lineTo(width.toFloat(), 0f)
        path.lineTo(0f, 0f)
        path.lineTo(0f, randStart)

    }

    private fun drawLast(width: Int, height: Int) {
        paint.color = colorList.last()
        val randX = Random.nextInt(0, width).toFloat()
        val randY = Random.nextInt(height / 4, height / 2).toFloat()
        val randStart = Random.nextInt(height * 3 / 4, height * 4 / 3).toFloat().toFloat()
        val randEnd = Random.nextInt(height / 2, height).toFloat()
        path.moveTo(0f, randStart)
        path.quadTo(randX, randY, width.toFloat(), randEnd)
        path.lineTo(width.toFloat(), height.toFloat())
        path.lineTo(0f, height.toFloat())
        path.lineTo(0f, randStart)
    }

    override fun setAlpha(alpha: Int) {
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
    }

    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT
}