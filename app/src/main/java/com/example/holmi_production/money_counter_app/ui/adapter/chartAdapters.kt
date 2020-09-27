package com.example.holmi_production.money_counter_app.ui.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import coil.load
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.extensions.toCurencyFormat
import com.example.holmi_production.money_counter_app.extensions.toRUformat
import com.example.holmi_production.money_counter_app.model.RecyclerItem
import com.example.holmi_production.money_counter_app.model.enums.Images
import com.example.holmi_production.money_counter_app.ui.adapter.items.CharCategoryItem
import com.example.holmi_production.money_counter_app.ui.adapter.items.ChartCategoryHeaderItem
import com.example.holmi_production.money_counter_app.ui.adapter.items.TransactionDashboardHeaderItem
import com.example.holmi_production.money_counter_app.ui.custom.CharItem
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegate
import kotlinx.android.synthetic.main.item_categories_details.view.*
import kotlinx.android.synthetic.main.item_categories_header.view.*
import kotlinx.android.synthetic.main.item_dashboard_transaction_header.view.*
import kotlinx.android.synthetic.main.item_percentage_categories.view.*
import java.lang.Math.abs
import java.util.*

fun top5categoriesAdapterDelegate() =
    adapterDelegate<CharCategoryItem, RecyclerItem>(CharCategoryItem.shortType) {
        bind {
            with(itemView) {
                val percent = "${String.format(Locale.getDefault(), "%.2f", item.percentage)}%"
                categoryName.text = item.categoryName
                percentage.text = percent
                circleIndicator.updateIndicator(
                    listOf(
                        CharItem(
                            angle = 360f,
                            color = item.color ?: Color.CYAN
                        )
                    )
                )
            }
        }
    }

fun chartCategoryDetailsAdapterDelegate() =
    adapterDelegate<CharCategoryItem, RecyclerItem>(CharCategoryItem.detailsType) {
        bind {
            with(itemView) {
                val percent = "${String.format(Locale.getDefault(), "%.2f", item.percentage)}%"
                detailsName.text = item.categoryName
                detailsPercentage.text = percent
                detailsSum.text = abs(item.sum).toString()
                percentView.setLinePercentage(item.percentage)
                colorIndicator.setColors(listOf(item.color ?: Color.GRAY))
                itemImage.load(Images.getImageById(item.categoryImage))

            }
        }
    }



fun chartCategoriesHeaderDelegate() =
    adapterDelegate<ChartCategoryHeaderItem, RecyclerItem>(ChartCategoryHeaderItem.VIEW_TYPE) {
        bind {
            with(itemView) {
                spendingType.text = item.spendingType
                totalSum.text = "Total: ${abs(item.sum).toCurencyFormat()}"
            }
        }
    }