package com.example.holmi_production.money_counter_app.useCases.impl

import com.example.holmi_production.money_counter_app.storage.db.SubCategoryDatabase
import com.example.holmi_production.money_counter_app.useCases.EditSubcategoryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
class EditSubcategoryUseCaseImpl(
    val db: SubCategoryDatabase
): EditSubcategoryUseCase {
    override suspend fun delete(subcategoryId: String) = withContext(Dispatchers.IO){
        db.delete(subcategoryId)
    }
}