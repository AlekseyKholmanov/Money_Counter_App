package com.example.holmi_production.money_counter_app.storage

import com.example.holmi_production.money_counter_app.model.entity.SubCategoryEntity
import com.example.holmi_production.money_counter_app.orm.ExpenseDatabase
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class SubCategoryRepository (
    val database: ExpenseDatabase
) {

    private val dao = database.subCategoryDao

    fun insert(category: SubCategoryEntity): Completable {
        return Completable.fromCallable { dao.insert(category) }
    }

    fun getSubCategories(): Single<List<SubCategoryEntity>> {
        return Single.fromCallable { dao.getCategories() }
    }

    fun observeSubCategories(): Flowable<List<SubCategoryEntity>> {
        return dao.observeCategories().distinctUntilChanged()
    }

    fun getSubcategoriesWithParentId(parentId: Int): Single<List<SubCategoryEntity>> {
        return Single.fromCallable {
            dao.getCategories()
                .filter { sub -> sub.categoryId == parentId }
        }
    }

    fun delete(subCategory: SubCategoryEntity): Completable {
        return Completable.fromCallable { dao.delete(subCategory) }
    }

    fun clear(): Completable {
        return Completable.fromCallable { dao.deleteAll() }
    }
}