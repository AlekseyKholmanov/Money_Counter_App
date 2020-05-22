package com.example.holmi_production.money_counter_app.storage.impl

import com.example.holmi_production.money_counter_app.model.entity.SubCategoryEntity
import com.example.holmi_production.money_counter_app.orm.ExpenseDatabase
import com.example.holmi_production.money_counter_app.storage.SubCategoryDatabase
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single


class SubCategoryDatabaseImpl(
    val database: ExpenseDatabase
) : SubCategoryDatabase {

    private val dao = database.subCategoryDao()

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