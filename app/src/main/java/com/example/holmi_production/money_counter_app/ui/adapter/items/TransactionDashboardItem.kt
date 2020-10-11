package com.example.holmi_production.money_counter_app.ui.adapter.items

import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.extensions.withCurrencyIcon
import com.example.holmi_production.money_counter_app.model.RecyclerItem
import com.example.holmi_production.money_counter_app.model.dto.TransactionDetailsDTO
import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity
import com.example.holmi_production.money_counter_app.model.entity.SubCategoryEntity
import com.example.holmi_production.money_counter_app.model.enums.CurrencyType
import com.example.holmi_production.money_counter_app.ui.adapter.decorators.LeftPaddedMarker
import org.joda.time.DateTime

class TransactionDashboardItem(
    val id: String,
    val createdDate: DateTime,
    val sum: Double,
    val category: CategoryEntity?,
    val subcategory: SubCategoryEntity?,
    val comment: String?,
    val isDeleted: Boolean,
    val currencyType: CurrencyType,
    val isConverted: Boolean
) : RecyclerItem, LeftPaddedMarker {
    companion object {
        const val VIEW_TYPE = R.layout.item_dashboard_transaction
        const val SUM = "TRANSACTION_SUM"
        const val CURRENCY_TYPE = "TRANSACTION_CURRENCY_TYPE"
        const val CATEGORY = "TRANSACTION_CATEGORY"
        const val SUBCATEGORY = "TRANSACTION_SUBCATEGORY"
    }

    override val padding: Int
        get() = 32
}

fun TransactionDetailsDTO.toDashboardItem(
    convertedSum: Double? = null,
    convertedCurrency: CurrencyType? = null
): TransactionDashboardItem {
    val sum = convertedSum ?: sum
    return TransactionDashboardItem(
        id = id,
        createdDate = createdDate,
        sum = convertedSum ?: sum,
        category = category,
        currencyType = convertedCurrency ?: currencyType,
        comment = comment,
        subcategory = subcategory,
        isDeleted = isDeleted,
        isConverted = convertedSum != null
    )
}
