package com.example.holmi_production.money_counter_app.ui.adapter.delegate

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import coil.load
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.extensions.toRUformat
import com.example.holmi_production.money_counter_app.model.RecyclerItem
import com.example.holmi_production.money_counter_app.model.enums.Images
import com.example.holmi_production.money_counter_app.ui.adapter.decorators.BaseDecorator
import com.example.holmi_production.money_counter_app.ui.adapter.diffUtil.DashboardDetailsItemDiffUtilCallback
import com.example.holmi_production.money_counter_app.ui.adapter.items.TransactionDashboardHeaderItem
import com.example.holmi_production.money_counter_app.ui.adapter.items.TransactionDashboardItem
import com.example.holmi_production.money_counter_app.ui.adapter.items.TransactionGroupItem
import com.example.holmi_production.money_counter_app.ui.adapter.items.toItem
import com.example.holmi_production.money_counter_app.ui.utils.dpToPx
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegate
import kotlinx.android.synthetic.main.item_dashboard_transaction.view.*
import kotlinx.android.synthetic.main.item_dashboard_transaction_header.view.*
import kotlinx.android.synthetic.main.item_transaction_group.view.*

fun dashboardTransactionDelegate() =
    adapterDelegate<TransactionGroupItem, RecyclerItem>(TransactionGroupItem.VIEW_TYPE) {
        val manager = AdapterDelegatesManager<List<RecyclerItem>>().apply {
            addDelegate(TransactionDashboardItem.VIEW_TYPE, dashboardTransactionDetailsDelegate())
            addDelegate(
                TransactionDashboardHeaderItem.VIEW_TYPE,
                dashboardTransactionDetailsHeaderDelegate()
            )
        }
        val adapter = AsyncListDifferDelegationAdapter(
            DashboardDetailsItemDiffUtilCallback(),
            manager
        )
        itemView.transactionGroup.adapter = adapter
        itemView.transactionGroup.addItemDecoration(BaseDecorator(context))

        bind {
            val items = mutableListOf<RecyclerItem>()
            items.add(
                TransactionDashboardHeaderItem(
                    date = item.date,
                    item.items.sumByDouble { it.sum })
            )
            item.items.forEach {
                items.add(
                    it.toItem()
                )
            }
            adapter.items = items
        }
    }

fun dashboardTransactionDetailsDelegate() =
    adapterDelegate<TransactionDashboardItem, RecyclerItem>(TransactionDashboardItem.VIEW_TYPE) {
        bind {
            with(itemView) {
                val lp = itemImage.layoutParams as ConstraintLayout.LayoutParams
                itemSum.text = item.sum.toString()
                itemCategory.text =
                    item.categoryId?.description ?: if (item.sum > 0) "Expense" else "Income"
                if (item.categoryId == null) {
                    indicator.visibility = View.GONE
                    if (item.sum > 0) {
                        itemImage.load(R.drawable.ic_money_got)
                    } else {
                        itemImage.load(R.drawable.ic_money_loss)
                    }
                    lp.marginStart = dpToPx(16).toInt()
                    itemImage.layoutParams = lp
                } else {
                    lp.marginStart = dpToPx(8).toInt()
                    itemImage.layoutParams = lp
                    itemImage.visibility = View.VISIBLE
                    itemImage.load(Images.getImageById(item.categoryId!!.imageId))
                    indicator.visibility = View.VISIBLE
                    indicator.setColors(listOf(item.categoryId!!.color))
                }
            }
        }
    }

fun dashboardTransactionDetailsHeaderDelegate() =
    adapterDelegate<TransactionDashboardHeaderItem, RecyclerItem>(TransactionDashboardHeaderItem.VIEW_TYPE) {
        bind {
            with(itemView) {
                headerDate.text = item.date.toRUformat()
                headerSum.text = "Total: ${item.total.toString()}"
            }
        }
    }