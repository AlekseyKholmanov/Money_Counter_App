package com.example.holmi_production.money_counter_app.useCases

interface AddRecentCategoryUseCase {

    suspend fun setRecentCategory(categoryId:String)

}