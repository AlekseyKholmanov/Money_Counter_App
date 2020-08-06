package com.example.holmi_production.money_counter_app.useCases

import android.graphics.drawable.ColorDrawable
import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity
import com.example.holmi_production.money_counter_app.model.entity.SubCategoryEntity

interface AddSubcategoryUseCase {

    suspend fun insert(category: SubCategoryEntity)

}