package com.example.holmi_production.money_counter_app.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.holmi_production.money_counter_app.model.enums.AccountType
import java.util.*

@Entity(tableName = "AccountTable")
data class AccountEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val description: String,
    val isHidden: Boolean,
    val isCalculatePerDaySam: Boolean,
    val accountType: AccountType,
    val password: String? = null

)