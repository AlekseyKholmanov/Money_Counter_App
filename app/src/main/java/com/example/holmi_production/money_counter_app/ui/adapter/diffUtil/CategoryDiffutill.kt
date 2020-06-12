package com.example.holmi_production.money_counter_app.ui.adapter.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.example.holmi_production.money_counter_app.model.Item
import com.example.holmi_production.money_counter_app.ui.adapter.items.CategoryItem
import com.example.holmi_production.money_counter_app.ui.adapter.items.ZeroItem

class CategoryDiffutill : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {

        when {
            oldItem is CategoryItem && newItem is CategoryItem -> {
                return oldItem.categoryId == newItem.categoryId
            }
            oldItem is ZeroItem && newItem is ZeroItem -> {
                return true
            }
        }
        return false
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        when {
            oldItem is CategoryItem && newItem is CategoryItem -> {
                return oldItem.description == newItem.description &&
                        oldItem.color == newItem.color &&
                        oldItem.imageId == newItem.imageId &&
                        oldItem.withSubcategory == newItem.withSubcategory
            }
            oldItem is ZeroItem && newItem is ZeroItem -> {
                return true
            }
        }
        return false
    }
}