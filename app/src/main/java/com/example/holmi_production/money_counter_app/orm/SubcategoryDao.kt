package com.example.holmi_production.money_counter_app.orm

import androidx.room.*
import com.example.holmi_production.money_counter_app.model.entity.SubCategoryEntity
import io.reactivex.Flowable

@Dao
interface SubcategoryDao  {
    @Query("SELECT * FROM SubCategoryEntity")
    fun observeCategories(): Flowable<List<SubCategoryEntity>>

    @Query("SELECT * FROM SubCategoryEntity")
    fun getCategories():List<SubCategoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(category: SubCategoryEntity)

    @Delete
    fun delete(category: SubCategoryEntity)

    @Query("DELETE FROM CategoryEntity")
    fun deleteAll()
}