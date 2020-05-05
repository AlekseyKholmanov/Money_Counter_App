package com.example.holmi_production.money_counter_app.storage

import com.example.holmi_production.money_counter_app.model.CategoryDetails

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
interface CategoryRepository {

    suspend fun getCategoryDetailsById(categoryId:Int): CategoryDetails

}