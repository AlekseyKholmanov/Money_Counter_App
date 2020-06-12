package com.example.holmi_production.money_counter_app.model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.holmi_production.money_counter_app.model.entity.AccountEntity
import com.example.holmi_production.money_counter_app.model.entity.TransactionEntity

data class AccountDetails (
    @Embedded
    val account: AccountEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "accountId"
    )
    val transactions: List<TransactionEntity>
)
