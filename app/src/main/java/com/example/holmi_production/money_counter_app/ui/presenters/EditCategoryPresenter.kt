package com.example.holmi_production.money_counter_app.ui.presenters

import moxy.InjectViewState
import com.example.holmi_production.money_counter_app.interactor.CategoryInteractor
import com.example.holmi_production.money_counter_app.mvp.BasePresenter
import javax.inject.Inject

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
@InjectViewState
class EditCategoryPresenter (
    private val interactor: CategoryInteractor
) :    BasePresenter<EditCategoryView>() {

    fun getCategoryById(categoryId:Int){

    }

    fun updateCategory(){

    }

}