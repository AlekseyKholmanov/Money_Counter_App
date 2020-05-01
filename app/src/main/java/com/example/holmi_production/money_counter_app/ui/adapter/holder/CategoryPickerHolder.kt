package com.example.holmi_production.money_counter_app.ui.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity
import com.example.holmi_production.money_counter_app.model.entity.SubCategoryEntity
import kotlinx.android.synthetic.main.item_category.view.*

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
class CategoryPickerHolder(
    view: View,
    private val callback: Callback
) : RecyclerView.ViewHolder(view) {

    fun bind(pair: Pair<CategoryEntity, List<SubCategoryEntity>>) {
        with(itemView) {
            val (category, subcategories) = pair
            text_category_dialog.text = category.description
            setBackgroundColor(category.color)
            setOnClickListener {
                callback.categoryPicked(category.id)
            }
            setOnLongClickListener {
                callback.categoryEdited(pair)
                return@setOnLongClickListener true
            }
            image_subcategory_indicator.visibility = if (subcategories.isNotEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
            val array = resources.obtainTypedArray(R.array.images)
            val image = if (category.imageId != null) {
                array.getResourceId(category.imageId, R.drawable.ic_launcher_foreground)
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
        fun categoryEdited(pair: Pair<CategoryEntity, List<SubCategoryEntity>>)
    }
}