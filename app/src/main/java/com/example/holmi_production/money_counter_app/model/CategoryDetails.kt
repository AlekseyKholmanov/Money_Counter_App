package com.example.holmi_production.money_counter_app.model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity
import com.example.holmi_production.money_counter_app.model.entity.SubCategoryEntity
import com.example.holmi_production.money_counter_app.model.enums.Images
import com.example.holmi_production.money_counter_app.ui.adapter.items.CategoryItem

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

fun CategoryDetails.toItem(index: Int): CategoryItem {
    return CategoryItem(
        index = index,
        categoryId = this.category.id,
        description = this.category.description,
        color = this.category.color,
        imageResId = Images.getImageById(this.category.imageId),
        subcategories = this.subcategory ?: listOf(),
        transitionName = this.category.id
    )
}