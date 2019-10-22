package com.example.holmi_production.money_counter_app.storage

import com.example.holmi_production.money_counter_app.model.entity.SubCategory
import com.example.holmi_production.money_counter_app.orm.ExpenseDatabase
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class SubCategoryRepository @Inject constructor(
    val database: ExpenseDatabase
){

    private val dao = database.subCategoryDao

    fun insert(category: SubCategory): Completable {
        return Completable.fromCallable { dao.insert(category) }
    }

    fun getSubcategoriesWithParentId(parentId:Int): Single<List<SubCategory>> {
        return Single.fromCallable { dao.getCategories(parentId)}
    }

    fun clear():Completable{
        return  Completable.fromCallable{dao.deleteAll()}
    }
}