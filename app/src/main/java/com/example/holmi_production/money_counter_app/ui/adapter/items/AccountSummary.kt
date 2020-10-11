package com.example.holmi_production.money_counter_app.ui.adapter.items

import com.example.holmi_production.money_counter_app.model.enums.CurrencyType

class AccountSummary(
    val description: String,
    val accountId: String,
    val expenses: Double,
    val income: Double,
    val balance: Double,
    val currencyType: CurrencyType
)