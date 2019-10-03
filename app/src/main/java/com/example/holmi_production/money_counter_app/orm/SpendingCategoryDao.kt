package com.example.holmi_production.money_counter_app.orm

import androidx.room.*
import com.example.holmi_production.money_counter_app.model.entity.Spending
import com.example.holmi_production.money_counter_app.model.entity.SpendingCategory
import io.reactivex.Flowable
import io.reactivex.Maybe
import org.joda.time.DateTime

@Dao
interface SpendingCategoryDao {
    @Query("SELECT * FROM SPENDINGCATEGORY")
    fun observeSpending(): Flowable<List<Spending>>

    @Query("SELECT * FROM SpendingCategory")
    fun getCategorys():List<SpendingCategory>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(category: SpendingCategory)

    @Delete
    fun delete(category: SpendingCategory)

    @Query("DELETE FROM SpendingCategory")
    fun deleteAll()
}