package com.example.holmi_production.money_counter_app.keyboard

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.holmi_production.money_counter_app.R
import kotlinx.android.synthetic.main.fragment_category_picker_item.view.*

class CategoryItem @JvmOverloads constructor(
    color: Int,
    description: String,
    capture: Int,
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    init {
        val view = View.inflate(getContext(), R.layout.fragment_category_picker_item, this)
        view.setBackgroundColor(color)
        text_category_dialog.text = description
        val image = view.findViewById(R.id.image_category_dialog) as ImageView
        image.setImageResource(capture)
    }

}
