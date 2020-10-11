package com.example.holmi_production.money_counter_app.ui.adapter.delegate

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import coil.load
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.extensions.toCurencyFormat
import com.example.holmi_production.money_counter_app.extensions.toRUformat
import com.example.holmi_production.money_counter_app.extensions.withCurrencyIcon
import com.example.holmi_production.money_counter_app.model.RecyclerItem
import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity
import com.example.holmi_production.money_counter_app.model.enums.Images
import com.example.holmi_production.money_counter_app.ui.adapter.DashboardTransactionDetailsAdapter
import com.example.holmi_production.money_counter_app.ui.adapter.decorators.BaseItemDecorator
import com.example.holmi_production.money_counter_app.ui.adapter.diffUtil.DashboardDetailsItemDiffUtilCallback
import com.example.holmi_production.money_counter_app.ui.adapter.items.TransactionDashboardHeaderItem
import com.example.holmi_production.money_counter_app.ui.adapter.items.TransactionDashboardItem
import com.example.holmi_production.money_counter_app.ui.adapter.items.TransactionGroupItem
import com.example.holmi_production.money_counter_app.ui.utils.dpToPx
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegate
import kotlinx.android.synthetic.main.item_dashboard_transaction.view.*
import kotlinx.android.synthetic.main.item_dashboard_transaction_header.view.*
import kotlinx.android.synthetic.main.item_transaction_group.view.*

fun dashboardTransactionDelegate() =
    adapterDelegate<TransactionGroupItem, RecyclerItem>(TransactionGroupItem.VIEW_TYPE) {

        val detailsAdapter = DashboardTransactionDetailsAdapter()
        with(itemView.transactionGroup) {
            adapter = detailsAdapter
            addItemDecoration(BaseItemDecorator(context))
        }

        bind {
            val items = mutableListOf<RecyclerItem>()
            items.add(
                TransactionDashboardHeaderItem(
                    date = item.date,
                    item.items.sumByDouble { it.sum })
            )
            items.addAll(item.items)
            val diff = DiffUtil.calculateDiff(
                DashboardDetailsItemDiffUtilCallback(
                    detailsAdapter.items,
                    items
                )
            )
            detailsAdapter.items = items
            diff.dispatchUpdatesTo(detailsAdapter)
        }
    }

fun dashboardTransactionDetailsDelegate() =
    adapterDelegate<TransactionDashboardItem, RecyclerItem>(TransactionDashboardItem.VIEW_TYPE) {
        fun setCategory(category: CategoryEntity?) {
            with(itemView) {
                val lp = itemImage.layoutParams as ConstraintLayout.LayoutParams
                itemCategory.text =
                    category?.description ?: if (item.sum > 0) "Income" else "Expense"
                if (category == null) {
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
                    itemImage.load(Images.getImageById(category.imageId))
                    indicator.visibility = View.VISIBLE
                    indicator.setColors(listOf(category.color))
                }
            }
        }

        bind { payloads ->
            if (payloads.isEmpty()) {
                with(itemView) {
                    itemSum.text = item.sum.withCurrencyIcon(item.currencyType.icon)
                    setCategory(item.category)
                }
            } else {
                if (payloads is List<*>) {
                    payloads.forEach { payload ->
                        if (payload is String && payload == TransactionDashboardItem.CURRENCY_TYPE) {
                        }
                        if (payload is String && payload == TransactionDashboardItem.SUM) {
                            itemView.itemSum.text = item.sum.withCurrencyIcon(
                                item.currencyType.icon
                            )
                        }
                        if (payload is String && payload == TransactionDashboardItem.CATEGORY) {
                            setCategory(item.category)
                        }

                    }
                }
            }
        }
    }

fun dashboardTransactionDetailsHeaderDelegate() =
    adapterDelegate<TransactionDashboardHeaderItem, RecyclerItem>(TransactionDashboardHeaderItem.VIEW_TYPE) {
        bind {
            with(itemView) {
                headerDate.text = item.date.toRUformat()
                headerSum.text = "Total: ${item.total.toCurencyFormat()}"
            }
        }
    }