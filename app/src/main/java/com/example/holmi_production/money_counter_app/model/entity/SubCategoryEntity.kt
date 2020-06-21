package com.example.holmi_production.money_counter_app.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "SubcategoryTable")
data class SubCategoryEntity(

    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),

    val categoryId: String,

    val description: String,

    val isDeleted: Boolean = false,

    val color: Int
)