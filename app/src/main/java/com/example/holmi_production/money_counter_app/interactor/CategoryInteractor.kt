package com.example.holmi_production.money_counter_app.interactor

import android.graphics.drawable.ColorDrawable
import com.example.holmi_production.money_counter_app.extensions.async
import com.example.holmi_production.money_counter_app.model.SpDirection
import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity
import com.example.holmi_production.money_counter_app.model.entity.SubCategoryEntity
import com.example.holmi_production.money_counter_app.storage.CategoryRepository
import com.example.holmi_production.money_counter_app.storage.SubCategoryRepository
import com.example.holmi_production.money_counter_app.utils.ColorUtils
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.Flowables
import io.reactivex.rxkotlin.Singles
import javax.inject.Inject

class CategoryInteractor @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val subCategoryRepository: SubCategoryRepository) {

    fun insert(name: String, types: List<SpDirection>, color: ColorDrawable?, imageId:Int): Completable {

        val category = CategoryEntity(
            description = name,
            imageId = imageId,
            color = color?.color ?: ColorUtils.getColor(),
            spendingDirection = types
        )
        return insert(category)
    }

    fun insert(category: CategoryEntity): Completable {
        return categoryRepository.insert(category).async()
    }

    fun insert(subcategory: SubCategoryEntity): Completable {
        return subCategoryRepository.insert(subcategory)
    }

    fun delete(subcategory: SubCategoryEntity):Completable{
        return  subCategoryRepository.delete(subcategory)
    }

    fun observeCategories(): Flowable<MutableList<CategoryEntity>> {
        return categoryRepository.observeCategories()
            .async()
            .map { it.sortedByDescending { it.usageCount } }
            .map { it.toMutableList() }
    }

    fun observeSubcategories():Flowable<List<SubCategoryEntity>>{
        return subCategoryRepository.observeSubCategories()
    }

    fun observeCategoriesAndSubCategories(): Flowable<ArrayList<Pair<CategoryEntity, List<SubCategoryEntity>>>> {
        return Flowables.combineLatest(categoryRepository.observeCategories(), subCategoryRepository.observeSubCategories())
            .map { (categories, subCategories) ->
                val zipList = arrayListOf<Pair<CategoryEntity, List<SubCategoryEntity>>>()
                val sortedlist = categories.sortedByDescending { it.usageCount }
                sortedlist.forEach { category ->
                    zipList.add(Pair(category, subCategories.filter { it.parentId == category.id && !it.isDeleted}))
                }
                return@map zipList
            }
    }

    fun getCategoriesAndSubCategories(): Single<ArrayList<Pair<CategoryEntity, List<SubCategoryEntity>>>> {
        return Singles.zip(categoryRepository.getCategories(), subCategoryRepository.getSubCategories())
            .map { (categories, subCategories) ->
                val zipList = arrayListOf<Pair<CategoryEntity, List<SubCategoryEntity>>>()
                categories.forEach { category ->
                    zipList.add(Pair(category, subCategories.filter { it.parentId == category.id }))
                }
                return@map zipList
            }
    }

    fun getCategories(): Single<Array<CategoryEntity>> {
        return categoryRepository.getCategories().map {
            it.toTypedArray()
        }
    }

    fun getCategory(id: Int): Single<CategoryEntity> {
        return categoryRepository.getCategory(id)
    }

    fun getCategoryWithSub(id: Int): Single<Pair<CategoryEntity, List<SubCategoryEntity>>> {
        return Singles.zip(categoryRepository.getCategory(id), subCategoryRepository.getSubcategoriesWithParentId(id))
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