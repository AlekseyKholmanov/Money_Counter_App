package com.example.holmi_production.money_counter_app.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.holmi_production.money_counter_app.utils.currentTime

@Entity(tableName = "RecentCategory")
class RecentCategoryEntity(

    @PrimaryKey
    val categoryId: String,

    val timestamp: Long = currentTime

)