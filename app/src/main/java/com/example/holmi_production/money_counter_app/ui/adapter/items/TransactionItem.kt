package com.example.holmi_production.money_counter_app.ui.adapter.items

import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.Item
import com.example.holmi_production.money_counter_app.model.TransactionDetails
import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity
import com.example.holmi_production.money_counter_app.model.entity.SubCategoryEntity
import com.example.holmi_production.money_counter_app.model.entity.TransactionEntity
import com.example.holmi_production.money_counter_app.ui.adapter.delegates.TransactionItemDelegate

class TransactionItem(
    val transaction: TransactionEntity,
    val category: CategoryEntity?,
    val subcategory: SubCategoryEntity?
): Item {
    override val viewType: Int
        get() = TransactionItemDelegate.VIEW_TYPE
}

fun TransactionDetails.toItem(): TransactionItem{
    return TransactionItem(
        transaction = this.transaction,
        category = this.category,
        subcategory = this.subcategory
    )
}