package com.example.holmi_production.money_counter_app.useCases

import com.example.holmi_production.money_counter_app.model.entity.SubCategoryEntity
import kotlinx.coroutines.flow.Flow

interface GetSubcategoryUseCase {

    fun observeByCategoryId(categoryId:String): Flow<List<SubCategoryEntity>>

    suspend fun getAllByCategoryId(categoryId: String): List<SubCategoryEntity>
}