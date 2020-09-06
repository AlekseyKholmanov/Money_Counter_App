package com.example.holmi_production.money_counter_app.interactor

import android.graphics.drawable.ColorDrawable
import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity
import com.example.holmi_production.money_counter_app.model.entity.SubCategoryEntity
import com.example.holmi_production.money_counter_app.storage.db.CategoryDatabase
import com.example.holmi_production.money_counter_app.storage.db.SubCategoryDatabase
import com.example.holmi_production.money_counter_app.utils.ColorUtils

class CategoryInteractor(
    private val categoryDatabase: CategoryDatabase,
    private val subCategoryDatabase: SubCategoryDatabase
) {

    suspend fun insert(name: String, color: ColorDrawable?, imageId:Int) {

        val category = CategoryEntity(
            description = name,
            imageId = imageId,
            color = color?.color ?: ColorUtils.getColor()
        )
        insert(category)
    }

    suspend fun insert(category: CategoryEntity) {
         categoryDatabase.insert(category)
    }

   suspend fun insert(subcategory: SubCategoryEntity) {
        subCategoryDatabase.insert(subcategory)
    }

    suspend fun delete(subcategoryId: String){
        subCategoryDatabase.delete(subcategoryId)
    }


    suspend fun increaseUsageCount(categoryId: String)  {
            categoryDatabase.increaseUsageCount(categoryId)
    }

    suspend fun deleteAll() {
        return categoryDatabase.deleteAll()
    }
}