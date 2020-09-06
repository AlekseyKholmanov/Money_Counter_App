package com.example.holmi_production.money_counter_app.model.uiModels

import androidx.room.Embedded
import com.example.holmi_production.money_counter_app.model.entity.AccountEntity

class DashboardAccountDetails(
    val account: AccountEntity,

    val transactions: List<DashboardTransactionDetails>
)