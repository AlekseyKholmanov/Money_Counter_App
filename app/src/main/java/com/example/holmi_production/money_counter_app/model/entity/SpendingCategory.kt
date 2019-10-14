package com.example.holmi_production.money_counter_app.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.holmi_production.money_counter_app.model.SpDirection

@Entity
data class SpendingCategory(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val spendingDirection: SpDirection,
    val description: String,
    val color: Int?,
    val imageId: Int? = null,
    val usageCount:Int = 0
)

