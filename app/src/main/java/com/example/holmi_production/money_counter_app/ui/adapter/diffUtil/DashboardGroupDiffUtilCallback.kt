package com.example.holmi_production.money_counter_app.ui.adapter.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.example.holmi_production.money_counter_app.model.RecyclerItem
import com.example.holmi_production.money_counter_app.ui.adapter.items.TransactionGroupItem

class DashboardGroupDiffUtilCallback : DiffUtil.ItemCallback<RecyclerItem>() {
    override fun areItemsTheSame(oldItem: RecyclerItem, newItem: RecyclerItem): Boolean {
        return when {
            oldItem is TransactionGroupItem && newItem is TransactionGroupItem -> {
                oldItem.date == newItem.date
            }
            else -> true
        }
    }

    override fun areContentsTheSame(oldItem: RecyclerItem, newItem: RecyclerItem): Boolean {
        return when {
            oldItem is TransactionGroupItem && newItem is TransactionGroupItem -> {
                oldItem.items.hashCode() == newItem.items.hashCode()
            }
            else -> true
        }
    }
}