package com.example.holmi_production.money_counter_app.interactor

import android.graphics.drawable.ColorDrawable
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.extensions.async
import com.example.holmi_production.money_counter_app.model.SpDirection
import com.example.holmi_production.money_counter_app.model.entity.Category
import com.example.holmi_production.money_counter_app.storage.CategoryRepository
import com.example.holmi_production.money_counter_app.storage.SubCategoryRepository
import com.example.holmi_production.money_counter_app.utils.ColorUtils
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class CategoryInteractor @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val subCategoryRepository: SubCategoryRepository) {

    fun insert(name: String, types: List<SpDirection>, color: ColorDrawable?): Completable {

        val category = Category(
            description = name,
            imageId = R.drawable.ic_launcher_foreground,
            color = color?.color ?: ColorUtils.getColor(),
            spendingDirection = types
        )
        return insert(category)
    }

    fun insert(category: Category): Completable {
        return categoryRepository.insert(category).async()
    }

    fun observeCategories(): Flowable<MutableList<Category>> {
        return categoryRepository.observePeriod()
            .async()
            .map { it.sortedByDescending { it.usageCount } }
            .map { it.toMutableList() }
    }

    fun getCategories():Single<Array<Category>>{
        return categoryRepository.getCategories().map {
            it.toTypedArray()
        }
    }

    fun getCategory(id: Int): Single<Category> {
        return categoryRepository.getCategory(id).async()
    }

    fun updateUsageCount(categoryId: Int): Disposable {
        return getCategory(categoryId)
            .map { it.copy(usageCount = it.usageCount.plus(1)) }
            .subscribe({
                insert(it).async().subscribe()
            }, {})

    }

    fun deleteAll(): Completable {
        return categoryRepository.clear()
    }
}