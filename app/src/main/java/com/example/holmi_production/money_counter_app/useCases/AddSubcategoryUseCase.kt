package com.example.holmi_production.money_counter_app.useCases

import com.example.holmi_production.money_counter_app.model.entity.SubCategoryEntity

interface AddSubcategoryUseCase {

    suspend fun insert(category: SubCategoryEntity)

    suspend fun insertAll(subcategories: List<SubCategoryEntity>)

}