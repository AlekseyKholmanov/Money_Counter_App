package com.example.holmi_production.money_counter_app.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
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

    private val backgroundDrawable =
        ContextCompat.getDrawable(context, R.drawable.bg_rounded_corner_with_ripple_8dp)
    private val padding = dpToPx(4).toInt()

    init {
        inflate(context, R.layout.view_account_amount, this)
        orientation = VERTICAL
        gravity = GRAVITY_CENTER
        isClickable = true
        isFocusable = true
        background = backgroundDrawable
        setPadding(padding, padding, padding, padding)
        amountSize.text ="No info"

    }

    fun setTitleRes(@StringRes titleRes: Int) {
        val text = resources.getString(titleRes)
        amountTitle.text = text
    }

    fun setAmount(amount: String?) {
        amountSize.text = amount?: "No info"
    }
}