package com.example.holmi_production.money_counter_app.useCases

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
interface EditSubcategoryUseCase {
    suspend fun delete(subcategoryId: String)
}