package com.example.holmi_production.money_counter_app.ui.adapter.decorators

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.ui.utils.dpToPx
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class ListDelegationDecorator(context: Context) : RecyclerView.ItemDecoration() {

    private val bounds = RectF()
    private val padding16dp = dpToPx(32)
    private val dividerWidth = dpToPx(1)
    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG).apply {
        strokeWidth = dividerWidth
        color = ContextCompat.getColor(context, R.color.divider)
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val lm = parent.layoutManager ?: return
        val adapter = parent.adapter as? ListDelegationAdapter<*> ?: return

        for (i in 0 until parent.childCount) {
            val save = c.save()

            val child = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(child)
            if (position == RecyclerView.NO_POSITION)
                return
            val holderItem = adapter.items[position]

            when (holderItem) {
                is LeftPaddedMarker -> {
                    if (i != parent.childCount - 1) {
                        drawDivider(padding16dp, child, c, lm)
                        c.restoreToCount(save)
                    }
                }
                is FullLineMarker -> {
                    drawDivider(0f, child, c, lm)
                    c.restoreToCount(save)
                }
                else -> {

                }
            }
        }
    }

    private fun drawDivider(
        padding: Float,
        child: View,
        c: Canvas,
        lm: RecyclerView.LayoutManager
    ) {
        bounds.set(
            lm.getDecoratedLeft(child).toFloat() + padding,
            lm.getDecoratedTop(child).toFloat(),
            lm.getDecoratedRight(child).toFloat(),
            lm.getDecoratedBottom(child).toFloat()
        )
        c.drawLine(bounds.right, bounds.bottom, bounds.left, bounds.bottom, paint)
    }

}