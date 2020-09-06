package com.example.holmi_production.money_counter_app.orm

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.holmi_production.money_counter_app.model.entity.SubCategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SubcategoryDao {
    @Query("SELECT * FROM SubcategoryTable WHERE id=:subcategoryId AND isDeleted = 0")
    fun observeCategories(subcategoryId: String): Flow<List<SubCategoryEntity>>

    @Query("SELECT * FROM SubcategoryTable WHERE id=:subcategoryId AND isDeleted = 0")
    fun getCategories(subcategoryId: String): List<SubCategoryEntity>


    @Query("SELECT * FROM SubcategoryTable WHERE id=:subcategoryId AND isDeleted = 0")
    fun getSubcategoryById(subcategoryId: String): SubCategoryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(subcategory: SubCategoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(subcategories: List<SubCategoryEntity>)

    @Query("UPDATE SubcategoryTable SET isDeleted = 1 WHERE id=:subcategoryId")
    fun delete(subcategoryId: String)

    @Query("DELETE FROM SubcategoryTable")
    fun deleteAll()
}