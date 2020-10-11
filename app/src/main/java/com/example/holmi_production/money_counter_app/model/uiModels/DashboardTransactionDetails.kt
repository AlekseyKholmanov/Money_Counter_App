package com.example.holmi_production.money_counter_app.model.uiModels

import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity
import com.example.holmi_production.money_counter_app.model.entity.SubCategoryEntity
import com.example.holmi_production.money_counter_app.model.enums.CurrencyType
import org.joda.time.DateTime
import java.util.*

data class DashboardTransactionDetails(
    val id: String = UUID.randomUUID().toString(),

    val createdDate: DateTime,

    val sum: Double,

    val categoryId: CategoryEntity?,

    val subcategoryId: SubCategoryEntity?,

    val comment: String? = null,

    val isDeleted: Boolean = false,

    val currencyType: CurrencyType
)