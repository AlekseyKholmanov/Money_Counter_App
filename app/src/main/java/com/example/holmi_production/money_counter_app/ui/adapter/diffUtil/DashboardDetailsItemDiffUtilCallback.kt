package com.example.holmi_production.money_counter_app.ui.adapter.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.example.holmi_production.money_counter_app.model.RecyclerItem
import com.example.holmi_production.money_counter_app.ui.adapter.items.TransactionDashboardHeaderItem
import com.example.holmi_production.money_counter_app.ui.adapter.items.TransactionDashboardItem

class DashboardDetailsItemDiffUtilCallback : DiffUtil.ItemCallback<RecyclerItem>() {
    override fun areItemsTheSame(oldItem: RecyclerItem, newItem: RecyclerItem): Boolean {
        return when {
            oldItem is TransactionDashboardHeaderItem && newItem is TransactionDashboardHeaderItem -> oldItem.date == newItem.date

            oldItem is TransactionDashboardItem && newItem is TransactionDashboardItem -> oldItem.id == newItem.id

            else -> true
        }
    }

    override fun areContentsTheSame(oldItem: RecyclerItem, newItem: RecyclerItem): Boolean {
        return when {
            oldItem is TransactionDashboardHeaderItem && newItem is TransactionDashboardHeaderItem -> {
                      oldItem.total == newItem.total
            }
            oldItem is TransactionDashboardItem && newItem is TransactionDashboardItem -> {
                oldItem.categoryId == newItem.categoryId
                        && oldItem.subcategoryId == newItem.subcategoryId
                        && oldItem.createdDate == newItem.createdDate
                        && oldItem.comment == newItem.comment
                        && oldItem.isDeleted == newItem.isDeleted
                        && oldItem.sum == newItem.sum
            }
            else -> true
        }
    }
}