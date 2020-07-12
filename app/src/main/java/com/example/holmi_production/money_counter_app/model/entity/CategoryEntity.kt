package com.example.holmi_production.money_counter_app.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.holmi_production.money_counter_app.model.enums.SpDirection
import java.util.*


@Entity(tableName = "CategoryTable")
data class CategoryEntity(
    @PrimaryKey

    val id: String = UUID.randomUUID().toString(),

    val description: String,

    val color: Int,

    val imageId: Int,

    var usageCount: Int = 0,

    var isDeleted: Boolean = false

)
