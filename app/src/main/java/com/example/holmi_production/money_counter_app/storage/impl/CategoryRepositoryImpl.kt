package com.example.holmi_production.money_counter_app.storage.impl

import com.example.holmi_production.money_counter_app.model.CategoryDetails
import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity
import com.example.holmi_production.money_counter_app.orm.ExpenseDatabase
import com.example.holmi_production.money_counter_app.storage.CategoryRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class CategoryRepositoryImpl(
    val database: ExpenseDatabase
) : CategoryRepository {

    private val dao = database.categoryDao()

    fun insert(category: CategoryEntity): Completable {
        return Completable.fromCallable { dao.insert(category) }
    }

    fun getCategory(id: Int): Maybe<CategoryEntity> {
        return Maybe.fromCallable { dao.getCategory(id) }.subscribeOn(Schedulers.io())
    }

    fun getCategories(): Single<List<CategoryEntity>> {
        return Single.fromCallable {
            dao.getCategories()
        }
    }

    fun observeCategories(): Flowable<List<CategoryEntity>> {
        return dao.observeCategories().distinctUntilChanged()
    }

    fun clear(): Completable {
        return Completable.fromCallable { dao.deleteAll() }
    }

    //-----

    override suspend fun getCategoryDetailsById(categoryId: Int): CategoryDetails {
        return dao.getCategoryDetails(categoryId)
    }


}