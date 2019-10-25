package com.example.holmi_production.money_counter_app.extensions
import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.viewpager.widget.ViewPager
import kotlin.math.min

class WrapContentHeightViewPager : ViewPager {

    /**
     * Constructor
     *
     * @param context the context
     */
    constructor(context: Context) : super(context)

    /**
     * Constructor
     *
     * @param context the context
     * @param attrs the attribute set
     */
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        // find the first child view
        var height = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED))
            val h = child.measuredHeight
            if (h > height) height = h
        }

        if (height != 0) {
            height = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        }

        super.onMeasure(widthMeasureSpec, height)
    }

    /**
     * Determines the height of this view
     *
     * @param measureSpec A measureSpec packed into an int
     * @param view the base view with already measured height
     *
     * @return The height of the view, honoring constraints from measureSpec
     */
    private fun measureHeight(measureSpec: Int, view: View?): Int {
        var result = 0
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize
        } else {
            // set the height from the base view if available
            if (view != null) {
                result = view.measuredHeight
            }
            if (specMode == MeasureSpec.AT_MOST) {
                result = min(result, specSize)
            }
        }
        return result
    }

}