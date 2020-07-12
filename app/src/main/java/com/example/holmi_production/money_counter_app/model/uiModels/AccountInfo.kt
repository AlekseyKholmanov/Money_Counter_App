package com.example.holmi_production.money_counter_app.model.uiModels

import com.example.holmi_production.money_counter_app.model.AccountDetails
import com.example.holmi_production.money_counter_app.model.enums.AccountType

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
class AccountInfo(
    val id: String,
    val description: String,
    val isHidden: Boolean,
    val isCalculatePerDaySum: Boolean,
    val accountType: AccountType,
    val password: String? = null,
    val balance: Double
)

fun AccountDetails.toInfo(): AccountInfo {
    val balance = this.transactions.filter { !it.isDeleted }.sumByDouble { it.sum }
    return AccountInfo(
        id = this.account.id,
        description = this.account.description,
        isHidden = this.account.isHidden,
        isCalculatePerDaySum = this.account.isCalculatePerDaySum,
        accountType = this.account.accountType,
        password = this.account.password,
        balance = balance
    )
}