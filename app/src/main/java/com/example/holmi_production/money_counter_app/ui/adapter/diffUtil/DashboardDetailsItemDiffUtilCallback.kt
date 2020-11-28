package com.example.holmi_production.money_counter_app.ui.adapter.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.example.holmi_production.money_counter_app.model.RecyclerItem
import com.example.holmi_production.money_counter_app.ui.adapter.items.TransactionDashboardHeaderItem
import com.example.holmi_production.money_counter_app.ui.adapter.items.TransactionDashboardItem

class DashboardDetailsItemDiffUtilCallback(
    private val oldItems: List<RecyclerItem>,
    private val newItems: List<RecyclerItem>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]
        return when {
            oldItem is TransactionDashboardHeaderItem && newItem is TransactionDashboardHeaderItem -> {
                oldItem.date == newItem.date
            }

            oldItem is TransactionDashboardItem && newItem is TransactionDashboardItem -> {
                oldItem.id == newItem.id
            }

            else -> false
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]
        return when {
            oldItem is TransactionDashboardHeaderItem && newItem is TransactionDashboardHeaderItem -> {
                oldItem.total == newItem.total
            }
            oldItem is TransactionDashboardItem && newItem is TransactionDashboardItem -> {
                oldItem.category?.id == newItem.category?.id
                        && oldItem.subcategory?.id == newItem.subcategory?.id
                        && oldItem.currencyType == newItem.currencyType
                        && oldItem.comment == newItem.comment
                        && oldItem.isDeleted == newItem.isDeleted
                        && oldItem.sum == newItem.sum
            }
            else -> false
        }
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]
        val payloads = mutableListOf<Any>()
        when {
            oldItem is TransactionDashboardItem && newItem is TransactionDashboardItem -> {
                if (oldItem.sum != newItem.sum) {
                    payloads.add(TransactionDashboardItem.SUM)
                }
                if ((oldItem.category == null && newItem.category == null) || oldItem.category?.id != newItem.category?.id) {
                    payloads.add(TransactionDashboardItem.CATEGORY)
                }
                if (oldItem.subcategory?.id != newItem.subcategory?.id) {
                    payloads.add(TransactionDashboardItem.SUBCATEGORY)
                }
            }
        }
        return if (payloads.isEmpty()) {
            null
        } else {
            payloads
        }
    }

    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size
}
