package com.example.holmi_production.money_counter_app.keyboard

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.CategoryType
import kotlinx.android.synthetic.main.dialog_category_item.view.*

class CategoryItem @JvmOverloads constructor(
    type: CategoryType,
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    init {
        val view = View.inflate(getContext(), R.layout.dialog_category_item, this)
        view.setBackgroundColor(type.color)
        text_category_dialog.text = type.description
    }
}
