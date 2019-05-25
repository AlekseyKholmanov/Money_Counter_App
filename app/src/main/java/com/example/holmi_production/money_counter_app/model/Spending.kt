package com.example.holmi_production.money_counter_app.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Spending(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    var price: Float,
    var categoryTypes: CategoryTypes = CategoryTypes.NONE,
    var date: Date
)