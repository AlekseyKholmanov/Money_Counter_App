package com.example.holmi_production.money_counter_app.storage

import android.content.Context
import com.example.holmi_production.money_counter_app.model.entity.Category
import com.example.holmi_production.money_counter_app.orm.ExpenseDatabase
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class CategoryRepository @Inject constructor(
   val database: ExpenseDatabase
){

    private val dao = database.categoryDao

    fun insert(category: Category): Completable {
        return Completable.fromCallable { dao.insert(category) }
    }

    fun getCategory(id:Int) :Single<Category>{
        return  Single.fromCallable{dao.getCategory(id)}
    }

    fun getCategories():Single<List<Category>>{
        return Single.fromCallable {
            dao.getCategories()
        }
    }

    fun observeCategories(): Flowable<List<Category>> {
        return dao.observeCategories().distinctUntilChanged()
    }

    fun clear():Completable{
        return  Completable.fromCallable{dao.deleteAll()}
    }
}