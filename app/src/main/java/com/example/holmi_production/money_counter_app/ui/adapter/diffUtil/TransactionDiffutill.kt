package com.example.holmi_production.money_counter_app.ui.adapter.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.example.holmi_production.money_counter_app.model.Item
import com.example.holmi_production.money_counter_app.ui.adapter.items.TransactionDayHeaderItem
import com.example.holmi_production.money_counter_app.ui.adapter.items.TransactionItem
import com.example.holmi_production.money_counter_app.ui.adapter.items.ZeroItem

class TransactionDiffutil:DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {

        when {
            oldItem is TransactionItem && newItem is TransactionItem -> {
                return oldItem.transaction.createdDate == newItem.transaction.createdDate
            }
            oldItem is TransactionDayHeaderItem && newItem is TransactionDayHeaderItem -> {
                oldItem.date == newItem.date
            }
            oldItem is ZeroItem && newItem is ZeroItem -> {
                return true
            }
        }
        return false
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        when {
            oldItem is TransactionItem && newItem is TransactionItem -> {
                return oldItem.transaction.sum == newItem.transaction.sum &&
                oldItem.transaction.categoryId == newItem.transaction.categoryId &&
                oldItem.transaction.subcategoryId == newItem.transaction.subcategoryId &&
                oldItem.transaction.accountId == newItem.transaction.accountId
            }
            oldItem is TransactionDayHeaderItem && newItem is TransactionDayHeaderItem -> {
                oldItem.date == newItem.date
            }
            oldItem is ZeroItem && newItem is ZeroItem -> {
                return true
            }
        }
        return false
    }
}