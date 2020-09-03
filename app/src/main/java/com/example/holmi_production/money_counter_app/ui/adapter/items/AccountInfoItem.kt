package com.example.holmi_production.money_counter_app.ui.adapter.items

import com.example.holmi_production.money_counter_app.model.AccountDetails
import com.example.holmi_production.money_counter_app.model.enums.AccountType
import com.example.holmi_production.money_counter_app.model.enums.CurrencyType

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
class AccountInfoItem(
    val id: String,
    val description: String,
    val isHidden: Boolean,
    val isCalculatePerDaySum: Boolean,
    val accountType: AccountType,
    val password: String? = null,
    val balance: Double,
    val currencyType: CurrencyType
)

fun AccountDetails.toInfo(): AccountInfoItem {
    val balance = this.transactions.filter { !it.isDeleted }.sumByDouble { it.sum }
    return AccountInfoItem(
        id = this.account.id,
        description = this.account.description,
        isHidden = this.account.isHidden,
        isCalculatePerDaySum = this.account.isCalculatePerDaySum,
        accountType = this.account.accountType,
        password = this.account.password,
        balance = balance,
        currencyType = this.account.currencyType
    )
}