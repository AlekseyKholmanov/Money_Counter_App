package com.example.holmi_production.money_counter_app.storage.db

import com.example.holmi_production.money_counter_app.model.entity.SubCategoryEntity
import kotlinx.coroutines.flow.Flow

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
interface SubCategoryDatabase {

    suspend fun insert(subcategory: SubCategoryEntity)

    suspend fun delete(subcategoryId: String)

    fun observeSubCategories(categoryId: String): Flow<List<SubCategoryEntity>>

    suspend fun getAllByCategoryId(categoryId: String): List<SubCategoryEntity>

    suspend fun getSubcategoryById(subcategoryId: String): SubCategoryEntity?

    suspend fun insertAll(subcategories: List<SubCategoryEntity>)

}