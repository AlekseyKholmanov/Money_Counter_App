package com.example.holmi_production.money_counter_app.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import coil.load
import com.example.holmi_production.money_counter_app.R
import kotlinx.android.synthetic.main.view_checkable_category.view.*

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
class CompoundCheckableImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyle, defStyleRes), MCheckable {

    init {
        inflate(context, R.layout.view_checkable_category, this)
        background = ContextCompat.getDrawable(context, R.drawable.bg_rounded_8dp_stroked)
        val lp = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            .apply {
                marginEnd = 10
                marginStart = 10
                marginEnd = 10
            }
        layoutParams = lp
        isClickable = true
        isFocusable = true

    }

    private var mChecked = false
        set(value) {
            field = value
            imageIndicator.visibility = if (value) View.VISIBLE else View.GONE
        }

    fun setText(text: String) {
        text_category_dialog.text = text
    }

    fun setTextVisibility(isShowed: Boolean) {
        text_category_dialog.visibility = if (isShowed) View.VISIBLE else View.GONE
    }


    fun setImage(drawable: Int) {
        image_category_dialog.load(drawable) {
            placeholder(R.drawable.ic_launcher_foreground)
            error(R.drawable.ic_launcher_foreground)
        }
    }

    override fun isChecked(): Boolean = mChecked

    override fun toggle() {
        mChecked = !mChecked
    }

    override fun setChecked(checked: Boolean) {
        mChecked = checked
    }

}

interface MCheckable {
    /**
     * @return The current checked state of the view
     */
    fun isChecked(): Boolean

    /**
     * Change the checked state of the view
     *
     * @param checked The new checked state
     */
    fun setChecked(checked: Boolean)

    /**
     * Change the checked state of the view to the inverse of its current state
     *
     */
    fun toggle()
}