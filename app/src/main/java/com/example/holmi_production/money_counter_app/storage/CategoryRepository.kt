package com.example.holmi_production.money_counter_app.storage

import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity
import com.example.holmi_production.money_counter_app.orm.ExpenseDatabase
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class CategoryRepository (
   val database: ExpenseDatabase
){

    private val dao = database.categoryDao()

    fun insert(category: CategoryEntity): Completable {
        return Completable.fromCallable { dao.insert(category) }
    }

    fun getCategory(id:Int) :Single<CategoryEntity>{
        return  Single.fromCallable{dao.getCategory(id)}
    }

    fun getCategories():Single<List<CategoryEntity>>{
        return Single.fromCallable {
            dao.getCategories()
        }
    }

    fun observeCategories(): Flowable<List<CategoryEntity>> {
        return dao.observeCategories().distinctUntilChanged()
    }

    fun clear():Completable{
        return  Completable.fromCallable{dao.deleteAll()}
    }
}