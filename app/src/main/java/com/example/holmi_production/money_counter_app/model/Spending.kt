package com.example.holmi_production.money_counter_app.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.holmi_production.money_counter_app.costs.ListItem
import org.joda.time.DateTime

@Entity
data class Spending(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,

    val sum: Double,

    val categoryTypes: Expense,

    val spendingDate: DateTime

) : ListItem