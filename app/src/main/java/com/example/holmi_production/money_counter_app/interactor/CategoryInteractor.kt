package com.example.holmi_production.money_counter_app.interactor

import android.graphics.drawable.ColorDrawable
import com.example.holmi_production.money_counter_app.model.CategoryDetails
import com.example.holmi_production.money_counter_app.model.SpDirection
import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity
import com.example.holmi_production.money_counter_app.model.entity.SubCategoryEntity
import com.example.holmi_production.money_counter_app.storage.CategoryDatabase
import com.example.holmi_production.money_counter_app.storage.SubCategoryDatabase
import com.example.holmi_production.money_counter_app.utils.ColorUtils
import kotlinx.coroutines.flow.Flow

class CategoryInteractor(
    private val categoryDatabase: CategoryDatabase,
    private val subCategoryDatabase: SubCategoryDatabase
) {

    suspend fun insert(name: String, types: List<SpDirection>, color: ColorDrawable?, imageId:Int) {

        val category = CategoryEntity(
            description = name,
            imageId = imageId,
            color = color?.color ?: ColorUtils.getColor(),
            spendingDirection = types
        )
        insert(category)
    }

    suspend fun insert(category: CategoryEntity) {
         categoryDatabase.insert(category)
    }

   suspend fun insert(subcategory: SubCategoryEntity) {
        subCategoryDatabase.insert(subcategory)
    }

    suspend fun delete(subcategory: SubCategoryEntity){
        subCategoryDatabase.delete(subcategory)
    }

    fun observeCategories(): Flow<List<CategoryEntity>> {
        return categoryDatabase.observeCategories()
    }

    fun observeSubcategories():Flow<List<SubCategoryEntity>>{
        return subCategoryDatabase.observeSubCategories()
    }

    fun observeCategoriesDetails(): Flow<List<CategoryDetails>>{
        return categoryDatabase.observeCategoriesDetails()
    }

    suspend fun getCategories(): List<CategoryEntity> {
        return categoryDatabase.getCategories()
    }

    suspend fun getCateforyDetails(categoryId: Int): CategoryDetails {
        return categoryDatabase.getCategoryDetailsById(categoryId)
    }

    suspend fun increaseUsageCount(categoryId: Int)  {
            categoryDatabase.increaseUsageCount(categoryId)
    }

    suspend fun deleteAll() {
        return categoryDatabase.deleteAll()
    }
}