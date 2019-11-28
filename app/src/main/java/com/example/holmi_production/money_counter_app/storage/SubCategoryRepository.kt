package com.example.holmi_production.money_counter_app.storage

import com.example.holmi_production.money_counter_app.model.entity.SubCategory
import com.example.holmi_production.money_counter_app.orm.ExpenseDatabase
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class SubCategoryRepository @Inject constructor(
    val database: ExpenseDatabase
) {

    private val dao = database.subCategoryDao

    fun insert(category: SubCategory): Completable {
        return Completable.fromCallable { dao.insert(category) }
    }

    fun getSubCategories(): Single<List<SubCategory>> {
        return Single.fromCallable { dao.getCategories() }
    }

    fun observeSubCategories(): Flowable<List<SubCategory>> {
        return dao.observeCategories().distinctUntilChanged()
    }

    fun getSubcategoriesWithParentId(parentId: Int): Single<List<SubCategory>> {
        return Single.fromCallable {
            dao.getCategories()
                .filter { sub -> sub.parentId == parentId }
        }
    }

    fun delete(subCategory: SubCategory): Completable {
        return Completable.fromCallable { dao.delete(subCategory) }
    }

    fun clear(): Completable {
        return Completable.fromCallable { dao.deleteAll() }
    }
}