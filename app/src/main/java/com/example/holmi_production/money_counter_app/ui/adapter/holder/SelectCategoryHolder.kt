package com.example.holmi_production.money_counter_app.ui.adapter.holder

import android.content.res.ColorStateList
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.holmi_production.money_counter_app.model.enums.Images
import com.example.holmi_production.money_counter_app.ui.adapter.items.CategoryItem
import kotlinx.android.synthetic.main.item_category.view.*

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
class SelectCategoryHolder(
    view: View
) : RecyclerView.ViewHolder(view) {

    fun bind(item: CategoryItem) {
        with(itemView) {
            with(categoryItem) {
                setText(item.description)
                backgroundTintList = ColorStateList.valueOf(item.color)
                setImage(item.imageResId)
            }
        }
    }

    interface Callback {
        fun categoryPicked(index: Int, categoryId: String?)
        fun createCategorySelected()
        fun categoryEdited(categoryId: String?)
    }
}