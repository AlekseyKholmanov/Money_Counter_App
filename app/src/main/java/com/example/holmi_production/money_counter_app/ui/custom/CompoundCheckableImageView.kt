package com.example.holmi_production.money_counter_app.ui.custom

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.View
import android.widget.Checkable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import coil.api.load
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
) : ConstraintLayout(context, attrs, defStyle, defStyleRes), Checkable {

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

    fun setText(text: String) {
        text_category_dialog.text = text
    }

    fun setImage(drawable: Int) {
        image_category_dialog.load(drawable) {
            placeholder(R.drawable.ic_launcher_foreground)
            error(R.drawable.ic_launcher_foreground)
        }
    }

    fun setChecboxVisibility(isVisible: Boolean){
        checkbox.visibility = if(isVisible) View.VISIBLE else View.GONE
    }

    override fun isChecked(): Boolean {
        return checkbox.isChecked
    }

    override fun toggle() {
        checkbox.isChecked = !checkbox.isChecked
    }

    override fun setChecked(checked: Boolean) {
        checkbox.isChecked = checked
    }

}