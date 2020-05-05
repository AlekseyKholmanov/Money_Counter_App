package com.example.holmi_production.money_counter_app.model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity
import com.example.holmi_production.money_counter_app.model.entity.SubCategoryEntity

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
data class CategoryDetails(
    @Embedded
    val category: CategoryEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val subcategory: List<SubCategoryEntity>?
)