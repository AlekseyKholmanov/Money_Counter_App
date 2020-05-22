package com.example.holmi_production.money_counter_app.interactor

import android.graphics.drawable.ColorDrawable
import com.example.holmi_production.money_counter_app.extensions.async
import com.example.holmi_production.money_counter_app.model.CategoryDetails
import com.example.holmi_production.money_counter_app.model.SpDirection
import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity
import com.example.holmi_production.money_counter_app.model.entity.SubCategoryEntity
import com.example.holmi_production.money_counter_app.storage.impl.CategoryDatabaseImpl
import com.example.holmi_production.money_counter_app.storage.impl.SubCategoryDatabaseImpl
import com.example.holmi_production.money_counter_app.utils.ColorUtils
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.Flowables
import io.reactivex.rxkotlin.Singles

class CategoryInteractor(
    private val categoryRepository: CategoryDatabaseImpl,
    private val subCategoryDatabase: SubCategoryDatabaseImpl
) {

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
        return subCategoryDatabase.insert(subcategory)
    }

    fun delete(subcategory: SubCategoryEntity):Completable{
        return  subCategoryDatabase.delete(subcategory)
    }

    fun observeCategories(): Flowable<MutableList<CategoryEntity>> {
        return categoryRepository.observeCategories()
            .async()
            .map { it.sortedByDescending { it.usageCount } }
            .map { it.toMutableList() }
    }

    fun observeSubcategories():Flowable<List<SubCategoryEntity>>{
        return subCategoryDatabase.observeSubCategories()
    }

    fun observeCategoriesAndSubCategories(): Flowable<ArrayList<Pair<CategoryEntity, List<SubCategoryEntity>>>> {
        return Flowables.combineLatest(categoryRepository.observeCategories(), subCategoryDatabase.observeSubCategories())
            .map { (categories, subCategories) ->
                val zipList = arrayListOf<Pair<CategoryEntity, List<SubCategoryEntity>>>()
                val sortedlist = categories.sortedByDescending { it.usageCount }
                sortedlist.forEach { category ->
                    zipList.add(Pair(category, subCategories.filter { it.categoryId == category.id && !it.isDeleted}))
                }
                return@map zipList
            }
    }

    fun getCategoriesAndSubCategories(): Single<ArrayList<Pair<CategoryEntity, List<SubCategoryEntity>>>> {
        return Singles.zip(categoryRepository.getCategories(), subCategoryDatabase.getSubCategories())
            .map { (categories, subCategories) ->
                val zipList = arrayListOf<Pair<CategoryEntity, List<SubCategoryEntity>>>()
                categories.forEach { category ->
                    zipList.add(Pair(category, subCategories.filter { it.categoryId == category.id }))
                }
                return@map zipList
            }
    }

    fun getCategories(): Single<Array<CategoryEntity>> {
        return categoryRepository.getCategories().map {
            it.toTypedArray()
        }
    }

    fun getCategory(id: Int): Maybe<CategoryEntity> {
        return categoryRepository.getCategory(id)
    }

    suspend fun getCateforyDetails(categoryId: Int): CategoryDetails {
        return categoryRepository.getCategoryDetailsById(categoryId)
    }

//    fun getCategoryWithSub(id: Int): Single<Pair<CategoryEntity, List<SubCategoryEntity>>> {
//        return Singles.zip(categoryRepository.getCategory(id).toSingle(), subCategoryRepository.getSubcategoriesWithParentId(id))
//    }

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