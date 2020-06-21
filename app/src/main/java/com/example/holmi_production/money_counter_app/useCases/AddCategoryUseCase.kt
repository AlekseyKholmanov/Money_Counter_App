package com.example.holmi_production.money_counter_app.useCases

import android.graphics.drawable.ColorDrawable
import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity

interface AddCategoryUseCase {

    suspend fun insert(name: String, color: ColorDrawable?, imageId:Int)

    suspend fun insert(category: CategoryEntity)

}