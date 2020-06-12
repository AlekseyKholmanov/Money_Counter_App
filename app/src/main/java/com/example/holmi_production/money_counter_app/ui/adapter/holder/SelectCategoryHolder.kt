package com.example.holmi_production.money_counter_app.ui.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.CategoryDetails
import com.example.holmi_production.money_counter_app.ui.adapter.items.CategoryItem
import kotlinx.android.synthetic.main.item_category.view.*

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
class SelectCategoryHolder(
    view: View,
    private val callback: Callback
) : RecyclerView.ViewHolder(view) {

    fun bind(item: CategoryItem) {
        with(itemView) {
            text_category_dialog.text = item.description
            setBackgroundColor(item.color)
            setOnClickListener {
                callback.categoryPicked(item.categoryId)
            }
            setOnLongClickListener {
                callback.categoryEdited(item.categoryId)
                return@setOnLongClickListener true
            }
            image_subcategory_indicator.visibility = if (item.withSubcategory) {
                View.VISIBLE
            } else {
                View.GONE
            }
            val array = resources.obtainTypedArray(R.array.images)
            val image = if (item.imageId != null) {
                array.getResourceId(item.imageId, R.drawable.ic_launcher_foreground)
            } else {
                R.drawable.ic_launcher_foreground
            }
            image_category_dialog.load(image) {
                placeholder(R.drawable.ic_launcher_foreground)
                error(R.drawable.ic_launcher_foreground)
            }
            array.recycle()
        }
    }

    interface Callback {
        fun categoryPicked(categoryId: Int)
        fun categoryEdited(categoryId: Int)
    }
}