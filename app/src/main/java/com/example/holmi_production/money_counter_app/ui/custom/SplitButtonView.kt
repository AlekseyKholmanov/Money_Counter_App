package com.example.holmi_production.money_counter_app.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.enums.SpDirection
import kotlinx.android.synthetic.main.view_splitted_button.view.*

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
class SplitButtonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {

    init {
        inflate(context, R.layout.view_splitted_button, this)
        orientation = VERTICAL
    }

    fun changeButtonState(state: List<SpDirection>) {
        val iconSize = when (state.size) {
            1 -> 144
            2 -> 96
            3 -> 72
            else -> throw Exception("out of range buton state")
        }

        if (state.contains(SpDirection.INCOME)) {
            keyIncome.visibility = View.VISIBLE
            keyIncome.iconSize = iconSize
        } else {
            keyIncome.visibility = View.GONE
        }
        if (state.contains(SpDirection.SPENDING)) {
            keySpending.visibility = View.VISIBLE
            keySpending.iconSize = iconSize
        } else {
            keySpending.visibility = View.GONE
        }
        if (state.contains(SpDirection.ACCUMULATION)) {
            keyAccumulation.visibility = View.VISIBLE
            keyAccumulation.iconSize = iconSize
        } else {
            keyAccumulation.visibility = View.GONE
        }
    }

}