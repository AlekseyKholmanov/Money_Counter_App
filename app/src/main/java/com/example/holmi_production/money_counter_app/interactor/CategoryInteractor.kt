package com.example.holmi_production.money_counter_app.interactor

import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.extensions.async
import com.example.holmi_production.money_counter_app.model.SpDirection
import com.example.holmi_production.money_counter_app.model.entity.Category
import com.example.holmi_production.money_counter_app.storage.CategoryRepository
import com.example.holmi_production.money_counter_app.storage.SubCategoryRepository
import com.example.holmi_production.money_counter_app.utils.ColorUtils
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class CategoryInteractor @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val subCategoryRepository: SubCategoryRepository) {

    fun insert(name: String, types: List<SpDirection>): Completable {

        val category = Category(
            description = name,
            imageId = R.drawable.ic_launcher_foreground,
            color = ColorUtils.getColor(),
            spendingDirection = types
        )
        return categoryRepository.insert(category).async()
    }

    fun observeCategories(): Flowable<MutableList<Category>> {
        return categoryRepository.observePeriod()
            .async()
            .map { it.sortedBy { it.usageCount } }
            .map { it.toMutableList() }

    }
}