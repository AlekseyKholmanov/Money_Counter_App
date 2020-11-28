package com.example.holmi_production.money_counter_app.model.dto

import androidx.room.Embedded
import androidx.room.Relation
import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity
import com.example.holmi_production.money_counter_app.model.entity.SubCategoryEntity
import com.example.holmi_production.money_counter_app.model.entity.TransactionEntity

data class TransactionDetailsDTO(
    @Embedded
    val transaction: TransactionEntity,

    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    val category: CategoryEntity?,

    @Relation(
        parentColumn = "subcategoryId",
        entityColumn = "id"
    )
    val subcategory: SubCategoryEntity?
)