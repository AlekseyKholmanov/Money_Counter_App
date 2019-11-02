package com.example.holmi_production.money_counter_app.orm

import androidx.room.*
import com.example.holmi_production.money_counter_app.model.entity.SubCategory
import io.reactivex.Flowable

@Dao
interface SubcategoryDao  {
    @Query("SELECT * FROM SubCategory")
    fun observeCategories(): Flowable<List<SubCategory>>

    @Query("SELECT * FROM SubCategory")
    fun getCategories():List<SubCategory>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(category: SubCategory)

    @Delete
    fun delete(category: SubCategory)

    @Query("DELETE FROM Category")
    fun deleteAll()
}