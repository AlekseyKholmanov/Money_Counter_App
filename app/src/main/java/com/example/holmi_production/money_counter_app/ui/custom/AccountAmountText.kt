package com.example.holmi_production.money_counter_app.ui.custom

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.ui.utils.dpToPx
import com.google.android.material.tabs.TabLayout.GRAVITY_CENTER
import kotlinx.android.synthetic.main.view_account_amount.view.*

class AccountAmountText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {


    private val selectedBackgroundColor =
        ContextCompat.getColor(context, R.color.checkedBackgroundColor)
    private val selectedColor = ContextCompat.getColor(context, R.color.selectedTextColor)
    private val normalTextColor = ContextCompat.getColor(context, R.color.textPrimaryInverted)
    private val normalBackgroundColor = Color.TRANSPARENT

    var isHiglited: Boolean = false
        set(value) {
            field = value
            updateBackground(value)
        }
    private val padding = dpToPx(4).toInt()

    init {
        inflate(context, R.layout.view_account_amount, this)
        orientation = VERTICAL
        gravity = GRAVITY_CENTER
        isClickable = true
        isFocusable = true
        background = getDrawable(context, R.drawable.bg_rounded_8dp)
        orientation = VERTICAL
        gravity = Gravity.CENTER
        setPadding(padding, padding, padding, padding)
        amountSize.text = "No info"
        isHiglited = false
    }

    fun setTitleRes(@StringRes titleRes: Int) {
        val text = resources.getString(titleRes)
        amountTitle.text = text
    }

    fun setAmount(amount: String?) {
        amountSize.text = amount ?: "No info"
    }

    private fun updateBackground(isHighlited: Boolean) {
        val color = when (isHighlited) {
            true -> selectedBackgroundColor
            false -> normalBackgroundColor
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N_MR1) {
            backgroundTintList = ColorStateList.valueOf(color)
        } else {
            val wrapped = DrawableCompat.wrap(background)
            wrapped.setTint(color)
            background = wrapped
        }
    }

    override fun setAlpha(alpha: Float) {
        amountSize.alpha = alpha
        amountTitle.alpha = alpha
    }
}