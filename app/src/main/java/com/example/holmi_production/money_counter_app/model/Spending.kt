package com.example.holmi_production.money_counter_app.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.DateTime

@Entity
data class Spending(
    @PrimaryKey(autoGenerate = false)
    val createdDate: DateTime,

    val sum: Double,

    val categoryType: Int,

    val isSpending: Boolean,

    val comment:String?

) : ListItem

