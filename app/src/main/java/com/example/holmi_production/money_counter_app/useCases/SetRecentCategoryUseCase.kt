package com.example.holmi_production.money_counter_app.useCases

interface SetRecentCategoryUseCase {

    suspend fun setRecentCategory(categoryId:String)

}