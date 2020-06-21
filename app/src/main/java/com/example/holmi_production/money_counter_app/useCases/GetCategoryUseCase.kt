package com.example.holmi_production.money_counter_app.useCases

import com.example.holmi_production.money_counter_app.model.CategoryDetails
import kotlinx.coroutines.flow.Flow

interface GetCategoryUseCase {

    fun observeCategoryDetailsById(categoryId:String?): Flow<CategoryDetails?>

    suspend fun getCategoryDetailsById(categoryId: String): CategoryDetails
}