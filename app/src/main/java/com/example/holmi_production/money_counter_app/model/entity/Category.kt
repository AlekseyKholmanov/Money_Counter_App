package com.example.holmi_production.money_counter_app.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.holmi_production.money_counter_app.model.SpDirection

@Entity
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val spendingDirection: List<SpDirection>,
    val description: String,
    val color: Int?,
    val imageId: Int? = null,
    var usageCount:Int = 0
)

