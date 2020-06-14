package com.example.holmi_production.money_counter_app.useCases

import com.example.holmi_production.money_counter_app.model.CategoryDetails
import kotlinx.coroutines.flow.Flow

interface GetRecentCategoryUseCase {

    fun observeResentCategory(): Flow<CategoryDetails?>

}