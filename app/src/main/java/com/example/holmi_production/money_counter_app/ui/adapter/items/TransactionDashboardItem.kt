package com.example.holmi_production.money_counter_app.ui.adapter.items

import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.Item
import com.example.holmi_production.money_counter_app.model.RecyclerItem
import com.example.holmi_production.money_counter_app.model.TransactionDetails
import com.example.holmi_production.money_counter_app.model.entity.AccountEntity
import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity
import com.example.holmi_production.money_counter_app.model.entity.SubCategoryEntity
import com.example.holmi_production.money_counter_app.model.entity.TransactionEntity
import com.example.holmi_production.money_counter_app.model.uiModels.DashboardTransactionDetails
import com.example.holmi_production.money_counter_app.ui.adapter.decorators.FullLineMarker
import com.example.holmi_production.money_counter_app.ui.adapter.decorators.LeftPaddedMarker
import com.example.holmi_production.money_counter_app.ui.adapter.delegate.TransactionItemDelegate
import org.joda.time.DateTime
import java.util.*

class   TransactionDashboardItem(
    val id: String,

    val createdDate: DateTime,

    val sum: Double,

    val categoryId: CategoryEntity?,

    val subcategoryId: SubCategoryEntity?,

    val comment: String?,

    val isDeleted: Boolean
) : RecyclerItem, LeftPaddedMarker {
    companion object{
        val VIEW_TYPE = R.layout.item_dashboard_transaction
    }
}

fun DashboardTransactionDetails.toItem(): TransactionDashboardItem {
    return TransactionDashboardItem(
        id = this.id,
        createdDate = this.createdDate,
        sum = this.sum,
        categoryId = this.categoryId,
        subcategoryId = this.subcategoryId,
        comment = this.comment,
        isDeleted = this.isDeleted,
    )
}