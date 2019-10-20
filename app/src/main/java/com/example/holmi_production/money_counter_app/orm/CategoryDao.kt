package com.example.holmi_production.money_counter_app.orm

import androidx.room.*
import com.example.holmi_production.money_counter_app.model.entity.Category
import io.reactivex.Flowable

@Dao
interface CategoryDao {
    @Query("SELECT * FROM Category")
    fun observeCategories(): Flowable<List<Category>>

    @Query("SELECT * FROM Category")
    fun getCategories():List<Category>

    @Query("SELECT * FROM Category WHERE id=:id")
    fun getCategory(id:Int):Category

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(category: Category)

    @Delete
    fun delete(category: Category)

    @Query("DELETE FROM Category")
    fun deleteAll()
}