package com.example.holmi_production.money_counter_app.storage.impl

import com.example.holmi_production.money_counter_app.model.CategoryDetails
import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity
import com.example.holmi_production.money_counter_app.orm.ExpenseDatabase
import com.example.holmi_production.money_counter_app.storage.CategoryDatabase
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class CategoryDatabaseImpl(
    val database: ExpenseDatabase
) : CategoryDatabase {

    private val dao = database.categoryDao()
    override fun observeCategories(): Flow<List<CategoryEntity>> {
        return dao.observeCategories()
    }

    override suspend fun insert(category: CategoryEntity) {
        withContext(Dispatchers.IO) { dao.insert(category) }
    }

    override suspend fun getAllCategoryDetails(): List<CategoryDetails> {
        return withContext(Dispatchers.IO) { dao.getAllCategoriesDetails() }
    }

    override suspend fun getAllCategories(): List<CategoryEntity> {
        return withContext(Dispatchers.IO) { dao.getCategories() }
    }

    override suspend fun increaseUsageCount(categoryId: Int) {
        withContext(Dispatchers.IO) { dao.increaseUsageCount(categoryId) }
    }

    override suspend fun getCategoryById(id: Int): CategoryEntity {
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

    override suspend fun getCategoryDetailsById(categoryId: Int): CategoryDetails {
        return dao.getCategoryDetails(categoryId)
    }



}