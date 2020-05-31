package com.example.holmi_production.money_counter_app.interactor

import android.graphics.drawable.ColorDrawable
import com.example.holmi_production.money_counter_app.extensions.async
import com.example.holmi_production.money_counter_app.model.CategoryDetails
import com.example.holmi_production.money_counter_app.model.SpDirection
import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity
import com.example.holmi_production.money_counter_app.model.entity.SubCategoryEntity
import com.example.holmi_production.money_counter_app.storage.CategoryDatabase
import com.example.holmi_production.money_counter_app.storage.SubCategoryDatabase
import com.example.holmi_production.money_counter_app.storage.impl.CategoryDatabaseImpl
import com.example.holmi_production.money_counter_app.storage.impl.SubCategoryDatabaseImpl
import com.example.holmi_production.money_counter_app.utils.ColorUtils
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.Flowables
import io.reactivex.rxkotlin.Singles
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CategoryInteractor(
    private val categoryRepository: CategoryDatabase,
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
         categoryRepository.insert(category)
    }

   suspend fun insert(subcategory: SubCategoryEntity) {
        subCategoryDatabase.insert(subcategory)
    }

    suspend fun delete(subcategory: SubCategoryEntity){
        subCategoryDatabase.delete(subcategory)
    }

    fun observeCategories(): Flow<List<CategoryEntity>> {
        return categoryRepository.observeCategories()
    }

    fun observeSubcategories():Flow<List<SubCategoryEntity>>{
        return subCategoryDatabase.observeSubCategories()
    }

    suspend fun getCategories(): List<CategoryEntity> {
        return categoryRepository.getCategories()
    }

    suspend fun getCateforyDetails(categoryId: Int): CategoryDetails {
        return categoryRepository.getCategoryDetailsById(categoryId)
    }

    suspend fun increaseUsageCount(categoryId: Int)  {
            categoryRepository.increaseUsageCount(categoryId)
    }

    suspend fun deleteAll() {
        return categoryRepository.deleteAll()
    }
}