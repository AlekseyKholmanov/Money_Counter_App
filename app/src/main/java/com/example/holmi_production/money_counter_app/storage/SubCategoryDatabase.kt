package com.example.holmi_production.money_counter_app.storage

import com.example.holmi_production.money_counter_app.model.entity.SubCategoryEntity
import kotlinx.coroutines.flow.Flow

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
interface SubCategoryDatabase {

    suspend fun insert(subcategory: SubCategoryEntity)

    suspend fun delete(subcategory: SubCategoryEntity)

    fun observeSubCategories(): Flow<List<SubCategoryEntity>>

}