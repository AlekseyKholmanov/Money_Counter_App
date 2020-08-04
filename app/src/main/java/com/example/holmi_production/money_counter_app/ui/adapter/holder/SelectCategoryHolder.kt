package com.example.holmi_production.money_counter_app.ui.adapter.holder

import android.content.res.ColorStateList
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.holmi_production.money_counter_app.R
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
                if(item.index == 0){
                    setChecboxVisibility(false)
                }
                val array = resources.obtainTypedArray(R.array.images)
                setText(item.description)
                val image = if (item.imageId != null) {
                    array.getResourceId(item.imageId, R.drawable.ic_launcher_foreground)
                } else {
                    R.drawable.ic_launcher_foreground
                }
                backgroundTintList = ColorStateList.valueOf(item.color)
                setImage(image)
                array.recycle()
            }
        }
    }

    interface Callback {
        fun categoryPicked(index: Int)
        fun categoryEdited(categoryId: String?)
    }
}