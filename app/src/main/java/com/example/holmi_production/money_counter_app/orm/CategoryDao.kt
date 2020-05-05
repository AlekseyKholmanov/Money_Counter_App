package com.example.holmi_production.money_counter_app.orm

import androidx.room.*
import com.example.holmi_production.money_counter_app.model.CategoryDetails
import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity
import io.reactivex.Flowable

@Dao
interface CategoryDao {
    @Query("SELECT * FROM CategoryEntity")
    fun observeCategories(): Flowable<List<CategoryEntity>>

    @Query("SELECT * FROM CategoryEntity")
    fun getCategories():List<CategoryEntity>

    @Query("SELECT * FROM CategoryEntity WHERE id=:id")
    fun getCategory(id:Int):CategoryEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(category: CategoryEntity)

    @Delete
    fun delete(category: CategoryEntity)

    @Query("DELETE FROM CategoryEntity")
    fun deleteAll()
    //---------

    @Query("SELECT * FROM CategoryEntity WHERE id=:categoryId")
    suspend fun getCategoryDetails(categoryId: Int): CategoryDetails

}