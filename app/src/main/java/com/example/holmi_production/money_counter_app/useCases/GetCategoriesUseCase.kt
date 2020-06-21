package com.example.holmi_production.money_counter_app.useCases

import com.example.holmi_production.money_counter_app.model.CategoryDetails
import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

interface GetCategoriesUseCase {

    fun observeCategoriesDetails(): Flow<List<CategoryDetails>>

    suspend fun getCategories(): List<CategoryEntity>


}