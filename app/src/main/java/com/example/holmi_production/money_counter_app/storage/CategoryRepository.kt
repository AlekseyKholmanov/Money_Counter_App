package com.example.holmi_production.money_counter_app.storage

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

    fun getPeriod(): Single<List<Category>> {
        return Single.fromCallable { dao.getCategories()}
    }

    fun getCategory(id:Int) :Single<Category>{
        return  Single.fromCallable{dao.getCategory(id)}
    }

    fun observePeriod(): Flowable<List<Category>> {
        return dao.observeCategories()
    }

    fun clear():Completable{
        return  Completable.fromCallable{dao.deleteAll()}
    }
}