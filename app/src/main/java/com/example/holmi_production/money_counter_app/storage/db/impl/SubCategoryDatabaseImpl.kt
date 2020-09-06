package com.example.holmi_production.money_counter_app.storage.db.impl

import com.example.holmi_production.money_counter_app.model.entity.SubCategoryEntity
import com.example.holmi_production.money_counter_app.orm.SubcategoryDao
import com.example.holmi_production.money_counter_app.storage.db.SubCategoryDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext


class SubCategoryDatabaseImpl(
    private val dao: SubcategoryDao
) : SubCategoryDatabase {

    override suspend fun insert(subcategory: SubCategoryEntity) = withContext(Dispatchers.IO) {
        dao.insert(subcategory)
    }

    override fun observeSubCategories(categoryId: String): Flow<List<SubCategoryEntity>> {
        return dao.observeCategories(categoryId)
    }

    override suspend fun getAllByCategoryId(categoryId: String): List<SubCategoryEntity> =
        withContext(Dispatchers.IO) {
            dao.getCategories(categoryId)
        }

    override suspend fun getSubcategoryById(subcategoryId: String): SubCategoryEntity? =
        withContext(Dispatchers.IO) {
            dao.getSubcategoryById(subcategoryId)
        }

    override suspend fun insertAll(subcategories: List<SubCategoryEntity>) =
        withContext(Dispatchers.IO) {
            dao.insertAll(subcategories)
        }

    override suspend fun delete(subcategoryId: String) = withContext(Dispatchers.IO) {
        dao.delete(subcategoryId)
    }


    suspend fun deleteALL() = withContext(Dispatchers.IO) {
        dao.deleteAll()
    }
}
