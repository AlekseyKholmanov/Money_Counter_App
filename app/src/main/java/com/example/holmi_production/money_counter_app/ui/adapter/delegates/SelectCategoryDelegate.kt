package com.example.holmi_production.money_counter_app.ui.adapter.delegates

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.Item
import com.example.holmi_production.money_counter_app.ui.adapter.holder.SelectCategoryHolder
import com.example.holmi_production.money_counter_app.ui.adapter.items.CategoryItem
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class SelectCategoryDelegate(private val callback: SelectCategoryHolder.Callback) :
    AbsListItemAdapterDelegate<CategoryItem, Item, SelectCategoryHolder>() {

    companion object {
        const val VIEW_TYPE = R.layout.item_category
    }

    override fun onCreateViewHolder(parent: ViewGroup): SelectCategoryHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(VIEW_TYPE, parent, false)
        return SelectCategoryHolder(view)
    }

    override fun isForViewType(item: Item, items: MutableList<Item>, position: Int): Boolean =
        item.viewType == VIEW_TYPE

    override fun onBindViewHolder(
        item: CategoryItem,
        holder: SelectCategoryHolder,
        payloads: MutableList<Any>
    ) {
        with(holder) {
            itemView.setOnClickListener {
                if (item.index != 0) {
                    callback.categoryPicked(item.index, item.categoryId)
                } else {
                    callback.createCategorySelected()
                }
            }
            itemView.setOnLongClickListener {
                if(item.index != 0) {
                    item.categoryId?.let{ callback.categoryEdited(it) }
                }
                true
            }
            bind(item)
        }
    }
}