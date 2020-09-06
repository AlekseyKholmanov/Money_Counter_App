package com.example.holmi_production.money_counter_app.useCases.impl

import com.example.holmi_production.money_counter_app.model.entity.SubCategoryEntity
import com.example.holmi_production.money_counter_app.storage.db.SubCategoryDatabase
import com.example.holmi_production.money_counter_app.useCases.AddSubcategoryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddSubcategoryUseCaseImpl(
    val db: SubCategoryDatabase
): AddSubcategoryUseCase {

    override suspend  fun insert(category: SubCategoryEntity)= withContext(Dispatchers.IO){
        db.insert(category)
    }

    override suspend fun insertAll(subcategories: List<SubCategoryEntity>) {
        db.insertAll(subcategories)
    }

}