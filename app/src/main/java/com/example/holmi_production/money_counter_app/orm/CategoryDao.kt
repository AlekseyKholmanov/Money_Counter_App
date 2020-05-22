package com.example.holmi_production.money_counter_app.orm

import androidx.room.Dao
import androidx.room.Query
import com.example.holmi_production.money_counter_app.model.CategoryDetails
import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity
import io.reactivex.Flowable

@Dao
abstract class CategoryDao : BaseDao<CategoryEntity>() {
    @Query("SELECT * FROM CategoryEntity")
    abstract fun observeCategories(): Flowable<List<CategoryEntity>>

    @Query("SELECT * FROM CategoryEntity")
    abstract fun getCategories(): List<CategoryEntity>

    @Query("SELECT * FROM CategoryEntity WHERE id=:id")
    abstract fun getCategory(id: Int): CategoryEntity

    @Query("DELETE FROM CategoryEntity")
    abstract fun deleteAll()
    //---------

    @Query("SELECT * FROM CategoryEntity WHERE id=:categoryId")
    abstract suspend fun getCategoryDetails(categoryId: Int): CategoryDetails

    @Query("SELECT * FROM CategoryEntity")
    abstract suspend fun getAllCategoriesDetails():List<CategoryDetails>

}