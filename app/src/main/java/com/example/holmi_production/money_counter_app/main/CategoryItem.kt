package com.example.holmi_production.money_counter_app.main

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.example.holmi_production.money_counter_app.R
import kotlinx.android.synthetic.main.dialog_category_item.view.*

class CategoryItem @JvmOverloads constructor(
    text: String,
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    init {
        View.inflate(getContext(), R.layout.dialog_category_item, this)
        text_category_dialog.text = text
    }
}
