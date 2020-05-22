package com.example.holmi_production.money_counter_app.storage

import com.example.holmi_production.money_counter_app.model.CategoryDetails
import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
interface CategoryDatabase {

    suspend fun getCategoryDetailsById(categoryId:Int): CategoryDetails

    suspend fun insert(category: CategoryEntity)

    suspend fun getAllCategoryDetails(): List<CategoryDetails>

    suspend fun getAllCategories(): List<CategoryEntity>

    suspend fun getCategoryById(id:Int): CategoryEntity

    suspend fun deleteAll()
}