package com.example.holmi_production.money_counter_app.useCases.impl

import com.example.holmi_production.money_counter_app.model.entity.SubCategoryEntity
import com.example.holmi_production.money_counter_app.storage.SubCategoryDatabase
import com.example.holmi_production.money_counter_app.useCases.GetSubcategoryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class GetSubcategoryUseCaseImpl(
    val db: SubCategoryDatabase
): GetSubcategoryUseCase {
    override fun observeByCategoryId(categoryId: String): Flow<List<SubCategoryEntity>> {
        return db.observeSubCategories(categoryId)
    }

    override suspend fun getAllByCategoryId(categoryId: String): List<SubCategoryEntity> = withContext(Dispatchers.IO){
        db.getAllByCategoryId(categoryId)
    }
}