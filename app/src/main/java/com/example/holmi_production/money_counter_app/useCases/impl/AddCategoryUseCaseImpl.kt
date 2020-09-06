package com.example.holmi_production.money_counter_app.useCases.impl

import android.graphics.drawable.ColorDrawable
import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity
import com.example.holmi_production.money_counter_app.storage.db.CategoryDatabase
import com.example.holmi_production.money_counter_app.useCases.AddCategoryUseCase
import com.example.holmi_production.money_counter_app.utils.ColorUtils

class AddCategoryUseCaseImpl(
    private val categoryDatabase: CategoryDatabase
): AddCategoryUseCase {

    override suspend fun insert(name: String, color: ColorDrawable?, imageId: Int) {

        val category = CategoryEntity(
            description = name,
            imageId = imageId,
            color = color?.color ?: ColorUtils.getColor()
        )
        insert(category)
    }

    override suspend fun insert(category: CategoryEntity) {
        categoryDatabase.insert(category)
    }

}