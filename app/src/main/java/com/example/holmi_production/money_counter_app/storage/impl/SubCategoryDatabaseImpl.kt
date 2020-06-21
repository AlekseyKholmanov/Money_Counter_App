package com.example.holmi_production.money_counter_app.storage.impl

import com.example.holmi_production.money_counter_app.model.entity.SubCategoryEntity
import com.example.holmi_production.money_counter_app.orm.ExpenseDatabase
import com.example.holmi_production.money_counter_app.orm.SubcategoryDao
import com.example.holmi_production.money_counter_app.storage.SubCategoryDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext


class SubCategoryDatabaseImpl(
    private val dao: SubcategoryDao
) : SubCategoryDatabase {

    override suspend fun insert(category: SubCategoryEntity) {
        withContext(Dispatchers.IO) { dao.insert(category) }
    }

    override fun observeSubCategories(): Flow<List<SubCategoryEntity>> {
        return dao.observeCategories()
    }

    override suspend fun delete(subCategory: SubCategoryEntity) {
        withContext(Dispatchers.IO) { dao.delete(subCategory) }
    }

    suspend fun deleteALL() {
        withContext(Dispatchers.IO) {
            dao.deleteAll()
        }
    }
}