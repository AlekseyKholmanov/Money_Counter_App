package com.example.holmi_production.money_counter_app.storage.db.impl

import com.example.holmi_production.money_counter_app.model.CategoryDetails
import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity
import com.example.holmi_production.money_counter_app.orm.CategoryDao
import com.example.holmi_production.money_counter_app.storage.db.CategoryDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class CategoryDatabaseImpl(
    private val dao: CategoryDao
) : CategoryDatabase {

    override fun observeCategories(): Flow<List<CategoryEntity>> {
        return dao.observeCategories()
    }

    override suspend fun insert(category: CategoryEntity) {
        withContext(Dispatchers.IO) { dao.upsert(category) }
    }

    override suspend fun getAllCategoryDetails(): List<CategoryDetails> {
        return withContext(Dispatchers.IO) { dao.getAllCategoriesDetails() }
    }

    override suspend fun getAllCategories(): List<CategoryEntity> {
        return withContext(Dispatchers.IO) { dao.getCategories() }
    }

    override suspend fun increaseUsageCount(categoryId: String) {
        withContext(Dispatchers.IO) { dao.increaseUsageCount(categoryId) }
    }

    override suspend fun getCategoryById(id: String): CategoryEntity {
        return withContext(Dispatchers.IO) { dao.getCategory(id) }
    }

    override suspend fun deleteAll() {
        return withContext(Dispatchers.IO) { dao.deleteAll() }
    }

    override suspend fun getCategories(): List<CategoryEntity> {
        return dao.getCategories()
    }

    override fun observeCategoriesDetails(): Flow<List<CategoryDetails>> {
        return dao.observeCategoriesDetails()
    }

    override suspend fun getCategoryDetailsById(categoryId: String): CategoryDetails {
        return dao.getCategoryDetails(categoryId)
    }

    override fun observeCategoryDetailsById(categoryId: String): Flow<CategoryDetails> {
        return dao.observeCategoryDetailsById(categoryId)
    }

}