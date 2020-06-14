package com.example.holmi_production.money_counter_app.orm

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.holmi_production.money_counter_app.model.CategoryDetails
import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity
import io.reactivex.Flowable
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CategoryDao : BaseDao<CategoryEntity>() {

    @Query("SELECT * FROM CategoryTable")
    abstract fun getCategories(): List<CategoryEntity>

    @Query("SELECT * FROM CategoryTable WHERE id=:id")
    abstract fun getCategory(id: String): CategoryEntity

    @Query("DELETE FROM CategoryTable")
    abstract fun deleteAll()

    @Query("SELECT * FROM CategoryTable")
    abstract fun observeCategories(): Flow<List<CategoryEntity>>

    @Transaction
    @Query("SELECT * FROM CategoryTable WHERE id=:categoryId")
    abstract fun getCategoryDetails(categoryId: String): CategoryDetails

    @Transaction
    @Query("SELECT * FROM CategoryTable")
    abstract fun getAllCategoriesDetails(): List<CategoryDetails>

    // update
    @Query("UPDATE CategoryTable SET usageCount = usageCount + 1 WHERE id =:categoryId")
    abstract fun increaseUsageCount(categoryId: String)

    @Transaction
    @Query("SELECT * FROM CategoryTable WHERE isDeleted = 0")
    abstract fun observeCategoriesDetails(): Flow<List<CategoryDetails>>

    @Transaction
    @Query("SELECT * FROM CategoryTable WHERE id=:categoryId")
    abstract fun observeCategoryDetailsById(categoryId: String): Flow<CategoryDetails>

}