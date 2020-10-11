package com.example.holmi_production.money_counter_app.model.dto

import androidx.room.Embedded
import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity
import com.example.holmi_production.money_counter_app.model.entity.SubCategoryEntity
import com.example.holmi_production.money_counter_app.model.enums.CurrencyType
import com.example.holmi_production.money_counter_app.ui.adapter.items.TransactionDashboardItem
import org.joda.time.DateTime

data class TransactionDetailsDTO(
    val id: String,

    val createdDate: DateTime,

    val sum: Double,

    val accountId: String,

    @Embedded(prefix = "category_")
    val category: CategoryEntity?,

    val currencyType: CurrencyType,

    @Embedded(prefix = "subcategory_")
    val subcategory: SubCategoryEntity?,

    val comment: String? = null,

    val isDeleted: Boolean = false
)