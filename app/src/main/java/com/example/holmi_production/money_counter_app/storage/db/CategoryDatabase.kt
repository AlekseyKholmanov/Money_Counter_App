package com.example.holmi_production.money_counter_app.storage.db

import com.example.holmi_production.money_counter_app.model.CategoryDetails
import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
interface CategoryDatabase {
    fun observeCategories(): Flow<List<CategoryEntity>>

    suspend fun insert(category: CategoryEntity)

    suspend fun increaseUsageCount(categoryId: String)

    suspend fun getCategoryDetailsById(categoryId: String): CategoryDetails

    fun observeCategoryDetailsById(categoryId: String): Flow<CategoryDetails>

    suspend fun getAllCategoryDetails(): List<CategoryDetails>

    suspend fun getAllCategories(): List<CategoryEntity>

    suspend fun getCategoryById(id:String): CategoryEntity

    suspend fun deleteAll()

    abstract suspend fun getCategories(): List<CategoryEntity>

    fun observeCategoriesDetails(): Flow<List<CategoryDetails>>
}