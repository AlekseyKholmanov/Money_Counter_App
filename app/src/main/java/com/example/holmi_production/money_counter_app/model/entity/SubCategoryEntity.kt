package com.example.holmi_production.money_counter_app.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SubcategoryTable")
data class SubCategoryEntity(

    @PrimaryKey(autoGenerate = true)
    override val id: Int = 0,
    val categoryId: Int,
    override val description: String,
    override val isDeleted: Boolean = false,
    override val color: Int
) : Nameble